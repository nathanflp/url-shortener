package com.nathanflp.url_shortener.mapper;

import com.nathanflp.url_shortener.domains.model.*;
import com.nathanflp.url_shortener.dtos.response.*;
import com.nathanflp.url_shortener.utils.*;
import org.springframework.data.domain.*;

import java.util.*;

public class ShortUrlMapper {

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

    public static ShortUrlPageResponse toPageResponse(Page<ShortUrl> page) {
        List<ShortUrlResponse> content = page.getContent()
                .stream()
                .map(shortUrl ->
                        ShortUrlMapper.toDefaultResponse(shortUrl.getId(), shortUrl))
                .toList();

        return new ShortUrlPageResponse(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
