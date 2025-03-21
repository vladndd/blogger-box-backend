package com.dauphine.blogger_box_backend.dto;

import java.util.UUID;

public class CategoryResponse {
    private UUID id;
    private String name;

    // Constructor
    public CategoryResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
