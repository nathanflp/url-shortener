package com.nathanflp.url_shortener.services;

import com.nathanflp.url_shortener.domains.generator.*;
import com.nathanflp.url_shortener.domains.model.*;
import com.nathanflp.url_shortener.dtos.request.*;
import com.nathanflp.url_shortener.exceptions.shortUrlExceptions.*;
import com.nathanflp.url_shortener.repositories.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

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
        logger.debug("Searching Url with id: {}", id);

        return shortUrlRepository.findById(id)
                .orElseThrow(() ->
                {
                    logger.warn("Url with id: {} not found", id);
                    return new UrlNotFoundException("URL not found with id: " + id);
                });
    }

    public ShortUrl shortenUrl(ShortUrlRequest shortUrlRequest) {
        logger.info("Checking if URL already has a shortened version, URL: {}", shortUrlRequest.originalUrl());

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findByOriginalUrl(shortUrlRequest.originalUrl());

        if(existingShortUrl.isPresent()){
            logger.info("URL already has a shortened version, returning existing short URL");
            return existingShortUrl.get();
        }

        logger.info("Starting URL shortening process");

        String shortenUrlId;

        do{
            logger.debug("Generating random id");
            shortenUrlId = linkIdGenerator.generateShortenUrlId();
            logger.debug("Generated id: {}", shortenUrlId);
        }
        while(doestLinkAlreadyExist(shortenUrlId));

        ShortUrl shortUrl = ShortUrl.createShortUrl(shortenUrlId, shortUrlRequest.originalUrl(), expirationDays);
        shortUrlRepository.save(shortUrl);

        logger.info("Short URL created successfully with id: {}", shortenUrlId);

        return shortUrl;
    }

    public String retrieveOriginalUrlFromShortenedUrl(String id) {
        logger.info("Redirect request received for id: {}", id);

        ShortUrl shortUrl = this.getShortUrlById(id);
        updateAccessedUrl(shortUrl);

        logger.info("Redirecting to original URL for id: {}", id);

        return shortUrl.getOriginalUrl();
    }

    public void updateAccessedUrl(ShortUrl shortUrlEntity){
        logger.debug("Updating access info for id: {}", shortUrlEntity.getId());
        shortUrlEntity.updateAccessedInfo(expirationDays);
        shortUrlRepository.save(shortUrlEntity);
    }

    public Boolean doestLinkAlreadyExist(String shortenUrlId){
        return shortUrlRepository.findById(shortenUrlId).isPresent();
    }

}
