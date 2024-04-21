package com.project.shorturl.controller;

import com.project.shorturl.controller.dto.RedirectUrlRequest;
import com.project.shorturl.service.GettingFullLink;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final GettingFullLink gettingFullLink;

    @GetMapping("/{shortUrl}")
    public String getLongUrl(@Valid @PathVariable RedirectUrlRequest shortUrl) {
        return gettingFullLink.getLongUrl(shortUrl.shortUrl());
    }

}