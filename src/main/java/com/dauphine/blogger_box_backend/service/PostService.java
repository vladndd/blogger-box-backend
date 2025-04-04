package com.dauphine.blogger_box_backend.service;

import com.dauphine.blogger_box_backend.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    /**
     * Get all posts by category ID
     * 
     * @param categoryId the category ID
     * @return list of posts in the category
     */
    List<Post> getAllByCategory(UUID categoryId);

    /**
     * Get all posts
     * 
     * @return list of all posts
     */
    List<Post> getAll();

    /**
     * Get post by ID
     * 
     * @param id the post ID
     * @return the post
     * @throws com.dauphine.blogger_box_backend.exception.PostNotFoundByIdException if no post found with given ID
     */
    Post getById(UUID id);

    /**
     * Create a new post
     * 
     * @param title the post title
     * @param content the post content
     * @param categoryId the category ID
     * @return the created post
     * @throws com.dauphine.blogger_box_backend.exception.CategoryNotFoundByIdException if the category ID is invalid
     */
    Post create(String title, String content, UUID categoryId);

    /**
     * Update an existing post
     * 
     * @param id the post ID to update
     * @param title the new title (or null to keep existing)
     * @param content the new content (or null to keep existing)
     * @return the updated post
     * @throws com.dauphine.blogger_box_backend.exception.PostNotFoundByIdException if no post found with given ID
     */
    Post update(UUID id, String title, String content);

    /**
     * Delete a post by ID
     * 
     * @param id the post ID to delete
     * @return true if deleted, false if not found
     */
    boolean deleteById(UUID id);

    /**
     * Get the latest posts ordered by creation date
     * 
     * @return list of posts ordered by creation date (newest first)
     */
    List<Post> getLatestPosts();
    
    /**
     * Search for posts by title
     * 
     * @param title the search term for post title
     * @return list of posts matching the title search
     */
    List<Post> searchByTitle(String title);
    
    /**
     * Search for posts by content
     * 
     * @param content the search term for post content
     * @return list of posts matching the content search
     */
    List<Post> searchByContent(String content);
}