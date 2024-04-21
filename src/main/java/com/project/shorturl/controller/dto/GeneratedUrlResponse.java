package com.project.shorturl.controller.dto;

import lombok.*;

@Getter
@Setter
public class GeneratedUrlResponse {

        private String url;

        public GeneratedUrlResponse(String shortUrl) {
                this.url = "http://localhost:8090/api/v1/" + shortUrl;
        }
}
