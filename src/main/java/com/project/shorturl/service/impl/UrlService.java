package com.project.shorturl.service.impl;

import com.project.shorturl.exception.GeneratedUrlException;
import com.project.shorturl.repository.UrlRepository;
import com.project.shorturl.service.GeneratorService;
import com.project.shorturl.service.GettingFullLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UrlService implements GeneratorService, GettingFullLink {

    public final UrlRepository urlRepository;

    @Override
    public String generateShortUrl(String longUrl) throws GeneratedUrlException {
        String base64String;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(longUrl.getBytes(StandardCharsets.UTF_8));
            base64String = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return base64String.substring(0, 8);
        } catch (Exception ex) {
            throw new GeneratedUrlException("Failed to generate short URL %s"
                    .formatted(longUrl) + ": " + ex.getMessage());
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        return null;
    }
}
