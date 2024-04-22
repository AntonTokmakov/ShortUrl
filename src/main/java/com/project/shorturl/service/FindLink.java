package com.project.shorturl.service;

public interface FindLink {

    String getShortUrl(String longUrl);

    String getLongUrl(String shortUrl);


}
