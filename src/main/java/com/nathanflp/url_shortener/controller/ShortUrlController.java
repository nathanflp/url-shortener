package com.nathanflp.url_shortener.controller;

import com.nathanflp.url_shortener.domain.model.*;
import com.nathanflp.url_shortener.dto.factory.*;
import com.nathanflp.url_shortener.dto.request.*;
import com.nathanflp.url_shortener.dto.response.*;
import com.nathanflp.url_shortener.service.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.*;

@RestController
@RequestMapping(value="/V1")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;
    @Value("${api.version}")
    private String api_version;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortUrlResponse> shortenUrl(@RequestBody ShortUrlRequest shortUrlRequest, HttpServletRequest servletRequest) {

        ShortUrl shortUrlEntity = shortUrlService.shortenUrl(shortUrlRequest);
        String baseUrl = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI(), "");
        String fullShortenUrl = baseUrl + "/" + api_version + "/" + shortUrlEntity.getId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ShortUrlDTOFactory.toDefaultResponse(fullShortenUrl, shortUrlEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirectFromShortenedUrl(@PathVariable("id") String id) {

        String originalUrl = shortUrlService.retrieveOriginalUrlFromShortenedUrl(id);

        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping(value = "/metrics/{id}")
    public ResponseEntity<ShortUrlMetricsResponse> metricsFromShortenedUrl(@PathVariable("id") String id) {
        ShortUrl shortUrlEntity = shortUrlService.getShortUrlById(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(ShortUrlDTOFactory.toMetricsResponse(shortUrlEntity));
    }

}
