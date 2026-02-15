package com.nathanflp.url_shortener.domains.generator;

import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class RandomLinkIdGenerator implements LinkIdGenerator {

    @Value("${id.length}")
    private int generatedIdLength;

    @Override
    public String generateShortenUrlId() {
        return RandomStringUtils.randomAlphanumeric(generatedIdLength);
    }

}
