package com.nathanflp.url_shortener.service;

import com.nathanflp.url_shortener.domain.generator.*;
import com.nathanflp.url_shortener.domain.model.*;
import com.nathanflp.url_shortener.dto.request.*;
import com.nathanflp.url_shortener.dto.response.*;
import com.nathanflp.url_shortener.repository.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final LinkIdGenerator linkIdGenerator;
    @Value("${default.expiration.days}")
    private int expirationDays;
    private final Logger logger = LoggerFactory.getLogger(ShortUrlService.class);

    public ShortUrlService(ShortUrlRepository shortUrlRepository, LinkIdGenerator linkIdGenerator) {
        this.shortUrlRepository = shortUrlRepository;
        this.linkIdGenerator = linkIdGenerator;
    }

    public ShortUrl getShortUrlById(String id) {
        return shortUrlRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    }

    public ShortUrl shortenUrl(ShortUrlRequest shortUrlRequest) {
        String shortenUrlId;

        do{
            shortenUrlId = linkIdGenerator.generateShortenUrlId();
        }
        while(doestLinkAlreadyExist(shortenUrlId));

        ShortUrl shortUrl = ShortUrl.createShortUrl(shortenUrlId, shortUrlRequest.originalUrl(), expirationDays);
        shortUrlRepository.save(shortUrl);

        return shortUrl;
    }

    public String retrieveOriginalUrlFromShortenedUrl(String id) {

        ShortUrl shortUrl = shortUrlRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));
        updateAccessedUrl(shortUrl);

        return shortUrl.getOriginalUrl();
    }

    public void updateAccessedUrl(ShortUrl shortUrlEntity){
        shortUrlEntity.updateAccessedInfo(expirationDays);
        shortUrlRepository.save(shortUrlEntity);
    }

    public Boolean doestLinkAlreadyExist(String shortenUrlId){
        return shortUrlRepository.findById(shortenUrlId).isPresent();
    }

}
