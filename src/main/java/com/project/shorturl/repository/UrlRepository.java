package com.project.shorturl.repository;

import com.project.shorturl.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findByLongUrl(String longUrl);

    boolean existsByLongUrl(String longUrl);
}
