package com.nathanflp.url_shortener.repositories;

import com.nathanflp.url_shortener.domains.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ShortUrlRepository extends JpaRepository<ShortUrl,Integer> {
    Optional<ShortUrl> findById(String shortenUrlId);
    Optional<ShortUrl> findByOriginalUrl(String originalUrl);
}
