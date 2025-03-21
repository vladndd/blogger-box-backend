package com.dauphine.blogger_box_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostResponse {
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private CategoryResponse category;

    // Constructor
    public PostResponse(UUID id, String title, String content, LocalDateTime createdAt, CategoryResponse category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.category = category;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }
}