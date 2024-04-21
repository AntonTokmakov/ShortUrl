package com.project.shorturl.controller;

import com.project.shorturl.controller.dto.GeneratedUrlRequest;
import com.project.shorturl.service.GeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generate")
public class GeneratedUrlController {

    public final GeneratorService generatorService;

    @PostMapping()
    public ResponseEntity<String> generateShortUrl(@RequestBody @Valid GeneratedUrlRequest request) {
        return ResponseEntity.ok()
                .body("Short url: " + generatorService.generateShortUrl(request.getLongUrl()));
    }


}
