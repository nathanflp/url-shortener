package com.nathanflp.url_shortener.services;

import com.nathanflp.url_shortener.domains.generator.*;
import com.nathanflp.url_shortener.domains.model.*;
import com.nathanflp.url_shortener.dtos.request.*;
import com.nathanflp.url_shortener.exceptions.shortUrlExceptions.*;
import com.nathanflp.url_shortener.repositories.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final LinkIdGenerator linkIdGenerator;
    @Value("${default.expiration.days}")
    private int expirationDays;
    @Value("${additional.days}")
    private int additionalDays;
    private final Logger logger = LoggerFactory.getLogger(ShortUrlService.class);

    public ShortUrlService(ShortUrlRepository shortUrlRepository, LinkIdGenerator linkIdGenerator) {
        this.shortUrlRepository = shortUrlRepository;
        this.linkIdGenerator = linkIdGenerator;
    }

    public ShortUrl getShortUrlById(String id) {
        logger.info("Searching Url with id: {}", id);

        return shortUrlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("URL not found for id: " + id) );
    }

    public ShortUrl shortenUrl(ShortUrlRequest shortUrlRequest) {
        logger.info("Checking if URL already has a shortened version, URL: {}", shortUrlRequest.urlToShorten());

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findByOriginalUrl(shortUrlRequest.urlToShorten());

        if(existingShortUrl.isPresent() && !existingShortUrl.get().isExpired()){
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

        ShortUrl shortUrl = ShortUrl.createShortUrl(shortenUrlId, shortUrlRequest.urlToShorten(), expirationDays);
        shortUrlRepository.save(shortUrl);

        logger.info("Short URL created successfully for id: {}", shortenUrlId);

        return shortUrl;
    }

    public String retrieveOriginalUrlFromShortenedUrl(String id) {
        logger.info("Redirect request received for id: {}", id);

        ShortUrl shortUrl = this.getShortUrlById(id);

        if(shortUrl.isExpired()){
            throw new UrlExpiredException("URL with id: " + id + " is expired since: " + shortUrl.getExpiresAt());
        }

        updateAccessedUrl(shortUrl);

        logger.info("Redirecting to original URL for id: {}", id);

        return shortUrl.getOriginalUrl();
    }

    public void updateAccessedUrl(ShortUrl shortUrlEntity){
        logger.info("Updating access info for id: {}", shortUrlEntity.getId());
        shortUrlEntity.updateAccessedInfo(additionalDays);
        shortUrlRepository.save(shortUrlEntity);
    }

    public Boolean doestLinkAlreadyExist(String shortenUrlId){
        return shortUrlRepository.findById(shortenUrlId).isPresent();
    }

    public Page<ShortUrl> findAllUrlsPaged(Pageable pageable) {
        return shortUrlRepository.findAll(pageable);
    }
}
