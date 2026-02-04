package com.nathanflp.url_shortener.service;

import com.nathanflp.url_shortener.controller.dto.request.*;
import com.nathanflp.url_shortener.controller.dto.response.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

@Service
public class ShortUrlService {

    public ShortUrlResponse shortenUrl(ShortUrlRequest shortUrlRequest, String string) {
        return null;
    }

    public HttpHeaders redirectFromShortenedUrl(String id) {
        return null;
    }

    public ShortUrlMetricsResponse metricsFromShortenedUrl(String id) {
        return null;
    }

}
