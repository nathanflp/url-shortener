package com.nathanflp.url_shortener.domains.model;

import jakarta.persistence.*;

import java.time.*;
import java.time.temporal.*;
import java.util.*;

@Entity
@Table(name = "short_urls")
public class ShortUrl {

    @Id
    private String id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "click_count", nullable = false)
    private int clickCount;

    @Column(name = "last_time_clicked")
    private Instant lastTimeClicked;

    public ShortUrl() {
    }

    private ShortUrl(String id, String originalUrl, Instant createdAt, Instant expiresAt, int clickCount) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.clickCount = clickCount;
    }

    public static ShortUrl createShortUrl(String id, String originalUrl, int expirationDays) {

        Instant createdAt = Instant.now();

        return new ShortUrl(
                id,
                originalUrl,
                createdAt,
                createdAt.plus(expirationDays, ChronoUnit.DAYS),
                0
        );
    }

    public String getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public int getClickCount() {
        return clickCount;
    }

    public Instant getLastTimeClicked() {
        return lastTimeClicked;
    }

    private void updateClickCount() {
        this.clickCount++;
    }

    private void updateLastTimeClicked() {
        this.lastTimeClicked = Instant.now();
    }

    private void updateExpiresAt(int days) {
            this.expiresAt = this.expiresAt.plus(days, ChronoUnit.DAYS);
        }

    public void updateAccessedInfo(int expirationDays) {
        updateClickCount();
        updateLastTimeClicked();
        updateExpiresAt(expirationDays);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresAt);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShortUrl shortUrl = (ShortUrl) o;
        return Objects.equals(id, shortUrl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
                "id='" + id + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", clickCount=" + clickCount +
                ", lastTimeClicked=" + lastTimeClicked +
                '}';
    }

}
