package com.project.shorturl.service.impl;

import com.project.shorturl.exception.*;
import com.project.shorturl.model.Url;
import com.project.shorturl.repository.UrlRepository;
import com.project.shorturl.service.FindLink;
import com.project.shorturl.service.GeneratorService;
import com.project.shorturl.service.RedirectService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class UrlService implements GeneratorService, RedirectService, FindLink {

    public final UrlRepository urlRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Value("${life.url}")
    private int lifeUrlMinutes;

    @Override
    public String getShortUrl(String longUrl) {
        Url url = urlRepository.findByLongUrl(longUrl)
                .orElseThrow(() -> new LinkNotFoundException("Short URL %s not found".formatted(longUrl)));
        return url.getShortUrl();
    }

    @Override
    public String generateShortUrl(String longUrl) {

        Future<String> link = executorService.submit(() -> {
            if (urlRepository.existsByLongUrl(longUrl)) {
                throw new ExistsLinkException("Long URL %s already exists.\n Use GET /{shortUrl}".formatted(longUrl));
            }

            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] hash = messageDigest.digest(longUrl.getBytes(StandardCharsets.UTF_8));
                String shortUrl = Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8);
                urlRepository.save(new Url(longUrl, shortUrl, ZonedDateTime.now()));
                return shortUrl;
            } catch (NoSuchAlgorithmException ex) {
                throw new GeneratedUrlException("Failed to generate short URL %s"
                        .formatted(longUrl) + ": " + ex.getMessage());
            }
        });

        try {
            return link.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ServiceUnavailableException("Service is currently overloaded. Please try again later.");
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new LinkNotFoundException("Short URL %s not found".formatted(shortUrl)));
        return url.getLongUrl();
    }

    @Override
    public HttpStatus redirectTo(String shortUrl, HttpServletResponse response){

            Url url = urlRepository.findByShortUrl(shortUrl)
                    .orElseThrow(() -> new LinkNotFoundException("Short URL %s not found".formatted(shortUrl)));

            if (!(url.getDateTime().plusMinutes(lifeUrlMinutes).isAfter(ZonedDateTime.now()))) {
                throw new LifeTimeExpiredException("Short URL %s expired".formatted(shortUrl));
            }

            try {
                response.sendRedirect(url.getLongUrl());
            } catch (IOException e) {
                throw new RedirectException("Failed to redirect to %s".formatted(url.getLongUrl()));
            }
            return HttpStatus.MOVED_PERMANENTLY;
    }
}
