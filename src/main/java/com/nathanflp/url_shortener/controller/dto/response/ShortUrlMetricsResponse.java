package com.nathanflp.url_shortener.controller.dto.response;

public record ShortUrlMetricsResponse(
                              String shortUrl,
                              String createdAt,
                              Integer clickCount,
                              String lastTimeClicked,
                              String expiresAt
                              ) {
}
