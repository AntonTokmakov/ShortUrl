package com.project.shorturl.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record RedirectUrlRequest(
        @NotBlank(message = "Ошибка, поле shortUrl не может быть пустым")
        @NotNull(message = "Ошибка, поле shortUrl не может быть null")
        @URL(message = "Ошибка, некорректная ссылка")
        String shortUrl) {
}
