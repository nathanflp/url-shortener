package com.nathanflp.url_shortener.dto.response;

public record ShortUrlMetricsResponse(
                              String urlId,
                              String originalUrl,
                              Integer clickCount,
                              String createdAt,
                              String lastTimeClicked,
                              String expiresAt
                              ) {
}
