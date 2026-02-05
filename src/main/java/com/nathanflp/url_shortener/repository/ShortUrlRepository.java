package com.nathanflp.url_shortener.repository;

import com.nathanflp.url_shortener.models.*;
import org.springframework.data.jpa.repository.*;

public interface ShortUrlRepository extends JpaRepository<ShortUrl,Integer> {
}
