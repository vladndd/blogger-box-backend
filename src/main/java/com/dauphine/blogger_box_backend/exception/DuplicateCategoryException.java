package com.dauphine.blogger_box_backend.exception;

public class DuplicateCategoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private final String categoryName;

    public DuplicateCategoryException(String name) {
        super("Category already exists with name: " + name);
        this.categoryName = name;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
}