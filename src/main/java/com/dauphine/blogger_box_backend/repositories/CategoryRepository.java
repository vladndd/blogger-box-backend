package com.dauphine.blogger_box_backend.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.dauphine.blogger_box_backend.models.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    
    /**
     * Find categories by name (case insensitive)
     * 
     * @param name the category name to search for
     * @return list of categories containing the name
     */
    @Query("""
        SELECT category
        FROM Category category
        WHERE UPPER(category.name) LIKE CONCAT('%', UPPER(:name), '%')
    """)
    List<Category> findAllLikeName(@Param("name") String name);
    
    /**
     * Find a category by its exact name (case insensitive)
     * 
     * @param name the exact category name to search for
     * @return the category with the exact name, or null if not found
     */
    @Query("""
        SELECT category
        FROM Category category
        WHERE UPPER(category.name) = UPPER(:name)
    """)
    Category findByNameIgnoreCase(@Param("name") String name);
}