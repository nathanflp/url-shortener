package com.nathanflp.url_shortener.models;

import jakarta.persistence.*;

import java.time.*;

@Entity
@Table(name = "urls")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_url")
    private String fullUrl;

    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "click_count")
    private Integer clickCount;

    @Column(name = "last_time_clicked")
    private LocalDateTime lastTimeClicked;

    public ShortUrl() {
    }

    public ShortUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public LocalDateTime getLastTimeClicked() {
        return lastTimeClicked;
    }

    public void setLastTimeClicked(LocalDateTime lastTimeClicked) {
        this.lastTimeClicked = lastTimeClicked;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @PrePersist
    private void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }

        if (this.clickCount == null) {
            this.clickCount = 0;
        }

        if (this.expiresAt == null) {
            this.expiresAt = this.createdAt.plusDays(3);
        }

    }

    public void increaseClickCount() {
        this.clickCount++;
    }

    public void updateLastTimeClicked() {
        this.lastTimeClicked = LocalDateTime.now();
    }

    public void updateExpiresAt(int days) {
            this.expiresAt = this.expiresAt.plusDays(days);
        }

    }
