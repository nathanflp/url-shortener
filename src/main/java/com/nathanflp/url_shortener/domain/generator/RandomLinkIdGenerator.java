package com.nathanflp.url_shortener.domain.generator;

import org.apache.commons.lang3.*;
import org.springframework.stereotype.*;

@Component
public class RandomLinkIdGenerator implements LinkIdGenerator {
    @Override
    public String generateShortenUrlId() {
        return RandomStringUtils.randomAlphanumeric(7);
    }

}
