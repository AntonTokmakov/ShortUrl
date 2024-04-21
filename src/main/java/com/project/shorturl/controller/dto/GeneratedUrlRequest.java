package com.project.shorturl.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

public record GeneratedUrlRequest (
        @NotBlank(message = "Ошибка, поле longUrl не может быть пустым")
        @NotNull(message = "Ошибка, поле longUrl не может быть null")
        @URL(message = "Ошибка, некорректная ссылка")
        String longUrl){
}
