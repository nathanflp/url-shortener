package com.nathanflp.url_shortener.controller;

import com.nathanflp.url_shortener.controller.dto.request.*;
import com.nathanflp.url_shortener.controller.dto.response.*;
import com.nathanflp.url_shortener.service.*;
import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/v1")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortUrlResponse> shortenUrl(@RequestBody ShortUrlRequest shortUrlRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(shortUrlService.shortenUrl(shortUrlRequest, httpServletRequest.getRequestURL().toString()) );
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Void> redirectFromShortenedUrl(@PathVariable("id") String id) {
        return ResponseEntity.
                status(HttpStatus.FOUND).
                headers(shortUrlService.redirectFromShortenedUrl(id))
                .build();
    }

    @GetMapping(value = "/metrics/{id}")
    public ResponseEntity<ShortUrlMetricsResponse> metricsFromShortenedUrl(@PathVariable("id") String id) {
        return ResponseEntity.
                status(HttpStatus.FOUND).
                body(shortUrlService.metricsFromShortenedUrl(id) );
    }

}
