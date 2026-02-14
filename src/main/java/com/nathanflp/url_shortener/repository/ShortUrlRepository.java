package com.nathanflp.url_shortener.repository;

import com.nathanflp.url_shortener.domain.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ShortUrlRepository extends JpaRepository<ShortUrl,Integer> {
    Optional<ShortUrl> findById(String shortenUrlId);
    Optional<ShortUrl> findByOriginalUrl(String shortenUrlId);
}
