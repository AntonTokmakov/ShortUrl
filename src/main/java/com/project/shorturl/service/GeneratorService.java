package com.project.shorturl.service;

import com.project.shorturl.exception.ExistsLinkException;

public interface GeneratorService {

    String generateShortUrl(String longUrl) throws ExistsLinkException;

}
