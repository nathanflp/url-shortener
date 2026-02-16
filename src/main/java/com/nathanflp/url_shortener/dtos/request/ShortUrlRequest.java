package com.nathanflp.url_shortener.dtos.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.*;

public record ShortUrlRequest(@NotBlank(message = "URL can't be blank")
                              @URL(protocol = "https", message = "URL must start with https://")
                              String urlToShorten) {
}
