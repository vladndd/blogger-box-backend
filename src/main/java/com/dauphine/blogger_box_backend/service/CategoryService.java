package com.dauphine.blogger_box_backend.service;

import com.dauphine.blogger_box_backend.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    /**
     * Get all categories
     * 
     * @return list of all categories
     */
    List<Category> getAll();

    /**
     * Get category by ID
     * 
     * @param id the category ID
     * @return the category or null if not found
     * @throws com.dauphine.blogger_box_backend.exception.CategoryNotFoundByIdException if no category found with given ID
     */
    Category getById(UUID id);

    /**
     * Create a new category
     * 
     * @param name the category name
     * @return the created category
     * @throws com.dauphine.blogger_box_backend.exception.DuplicateCategoryException if category with the name already exists
     */
    Category create(String name);

    /**
     * Update a category's name
     * 
     * @param id the category ID to update
     * @param name the new name
     * @return the updated category
     * @throws com.dauphine.blogger_box_backend.exception.CategoryNotFoundByIdException if no category found with given ID
     * @throws com.dauphine.blogger_box_backend.exception.DuplicateCategoryException if another category already has the name
     */
    Category updateName(UUID id, String name);

    /**
     * Delete a category by ID
     * 
     * @param id the category ID to delete
     * @return true if deleted, false if not found
     */
    boolean deleteById(UUID id);
    
    /**
     * Search for categories by name
     * 
     * @param name the search term for category name
     * @return list of categories matching the search
     */
    List<Category> searchByName(String name);
}