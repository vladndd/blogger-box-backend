package com.dauphine.blogger_box_backend.exception;

import java.util.UUID;

public class CategoryNotFoundByIdException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private final UUID categoryId;

    public CategoryNotFoundByIdException(UUID id) {
        super("Category not found with id: " + id);
        this.categoryId = id;
    }
    
    public UUID getCategoryId() {
        return categoryId;
    }
}