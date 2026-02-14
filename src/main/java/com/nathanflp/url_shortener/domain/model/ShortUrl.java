package com.nathanflp.url_shortener.domain.model;

import jakarta.persistence.*;

import java.time.*;
import java.time.temporal.*;

@Entity
@Table(name = "urls")
public class ShortUrl {

    @Id
    private String id;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "click_count")
    private Integer clickCount;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Instant getLastTimeClicked() {
        return lastTimeClicked;
    }

    public void setLastTimeClicked(Instant lastTimeClicked) {
        this.lastTimeClicked = lastTimeClicked;
    }

    public void updateClickCount() {
        this.clickCount++;
    }

    public void updateLastTimeClicked() {
        this.lastTimeClicked = Instant.now();
    }

    public void updateExpiresAt(int days) {
            this.expiresAt = this.expiresAt.plus(days, ChronoUnit.DAYS);
        }

    public void updateAccessedInfo(int expirationDays) {
        updateClickCount();
        updateLastTimeClicked();
        updateExpiresAt(expirationDays);
    }
}
