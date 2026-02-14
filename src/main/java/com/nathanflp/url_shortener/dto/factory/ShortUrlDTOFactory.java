package com.nathanflp.url_shortener.dto.factory;

import com.nathanflp.url_shortener.domain.model.*;
import com.nathanflp.url_shortener.dto.response.*;
import com.nathanflp.url_shortener.utils.*;

public class ShortUrlDTOFactory {

    public static ShortUrlResponse toDefaultResponse(String fullShortenUrl, ShortUrl shortUrl){
        return new ShortUrlResponse(fullShortenUrl,
                DateFormatter.formatInstant(shortUrl.getExpiresAt())
        );
    }


    public static ShortUrlMetricsResponse toMetricsResponse(ShortUrl shortUrlEntity) {
        return new ShortUrlMetricsResponse(
                shortUrlEntity.getId(),
                shortUrlEntity.getOriginalUrl(),
                shortUrlEntity.getClickCount(),
                DateFormatter.formatInstant(shortUrlEntity.getCreatedAt()),
                DateFormatter.formatInstant(shortUrlEntity.getLastTimeClicked()),
                DateFormatter.formatInstant(shortUrlEntity.getExpiresAt())
        );
    }
}
