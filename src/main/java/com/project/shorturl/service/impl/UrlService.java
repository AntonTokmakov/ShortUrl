package com.project.shorturl.service.impl;

import com.project.shorturl.exception.ExistsLinkException;
import com.project.shorturl.exception.GeneratedUrlException;
import com.project.shorturl.exception.LinkNotFoundException;
import com.project.shorturl.exception.RedirectException;
import com.project.shorturl.model.Url;
import com.project.shorturl.repository.UrlRepository;
import com.project.shorturl.service.GeneratorService;
import com.project.shorturl.service.RedirectService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UrlService implements GeneratorService, RedirectService {

    public final UrlRepository urlRepository;

    @Override
    public String getShortUrl(String longUrl) {
        Url url = urlRepository.findByShortUrl(longUrl)
                .orElseThrow(() -> new LinkNotFoundException("Short URL %s not found".formatted(longUrl)));
        return url.getShortUrl();
    }

    @Override
    public String generateShortUrl(String longUrl) throws ExistsLinkException {

        if (urlRepository.existsByLongUrl(longUrl)) {
            throw new ExistsLinkException("Long URL %s already exists".formatted(longUrl));
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(longUrl.getBytes(StandardCharsets.UTF_8));
            String shortUrl = Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8);
            urlRepository.save(new Url(longUrl, shortUrl, LocalDateTime.now()));
            return shortUrl;
        } catch (NoSuchAlgorithmException ex) {
            throw new GeneratedUrlException("Failed to generate short URL %s"
                    .formatted(longUrl) + ": " + ex.getMessage());
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new LinkNotFoundException("Short URL %s not found".formatted(shortUrl)));
        return url.getLongUrl();
    }

    @Override
    public HttpStatus redirectTo(String shortUrl, HttpServletResponse response) {

        String longUrl = getLongUrl(shortUrl);
        try {
            response.sendRedirect(longUrl);
        } catch (IOException e) {
            throw new RedirectException("Failed to redirect to %s".formatted(longUrl));
        }
        return HttpStatus.MOVED_PERMANENTLY;
    }
}
