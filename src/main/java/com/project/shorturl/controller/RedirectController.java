package com.project.shorturl.controller;

import com.project.shorturl.controller.dto.RedirectUrlRequest;
import com.project.shorturl.service.RedirectService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class RedirectController {

    private final RedirectService redirectService;

    @GetMapping("get/{shortUrl}")
    public String getLongUrl(@Valid @PathVariable RedirectUrlRequest shortUrl) {
        return redirectService.getLongUrl(shortUrl.shortUrl());
    }

    @GetMapping("{shortUrl}")
    public HttpStatus redirect(@Valid @PathVariable RedirectUrlRequest shortUrl, HttpServletResponse response) {
        return redirectService.redirectTo(shortUrl.shortUrl(), response);
    }
}