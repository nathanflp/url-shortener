package com.nathanflp.url_shortener.dto.response;

import java.time.*;

public record ShortUrlResponse(String shortenUrl, String expiresAt) {
}
