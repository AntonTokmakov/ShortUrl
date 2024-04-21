package com.project.shorturl.controller;

import com.project.shorturl.controller.dto.GeneratedUrlRequest;
import com.project.shorturl.controller.dto.GeneratedUrlResponse;
import com.project.shorturl.exception.ExistsLinkException;
import com.project.shorturl.service.GeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generate")
public class GeneratedUrlController {

    private final GeneratorService generatorService;

    @PostMapping()
    public GeneratedUrlResponse generateShortUrl(@RequestBody @Valid GeneratedUrlRequest request) {
        try {
            return new GeneratedUrlResponse(generatorService.generateShortUrl( request.longUrl()));
        } catch (ExistsLinkException e) {
            //todo redirect to find long url
            return null;
        }
    }
}
