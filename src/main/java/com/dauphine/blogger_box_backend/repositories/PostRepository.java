package com.dauphine.blogger_box_backend.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.dauphine.blogger_box_backend.models.Post;

public interface PostRepository extends JpaRepository<Post, UUID> {
    /**
     * Find posts by title containing the given string (case insensitive)
     * 
     * @param title the title to search for
     * @return list of posts with titles containing the search term
     */
    @Query("""
        SELECT post
        FROM Post post
        WHERE UPPER(post.title) LIKE CONCAT('%', UPPER(:title), '%')
    """)
    List<Post> findAllLikeTitle(@Param("title") String title);
    
    /**
     * Find posts by content containing the given string (case insensitive)
     * 
     * @param content the content to search for
     * @return list of posts with content containing the search term
     */
    @Query("""
        SELECT post
        FROM Post post
        WHERE UPPER(post.content) LIKE CONCAT('%', UPPER(:content), '%')
    """)
    List<Post> findAllLikeContent(@Param("content") String content);
    
    /**
     * Find posts by category ID
     * 
     * @param categoryId the ID of the category
     * @return list of posts for the specified category
     */
    @Query("""
        SELECT post
        FROM Post post
        WHERE post.category.id = :categoryId
    """)
    List<Post> findByCategory(@Param("categoryId") UUID categoryId);
    
    /**
     * Find the latest posts ordered by creation date (newest first)
     * 
     * @return list of posts ordered by creation date
     */
    @Query("""
        SELECT post
        FROM Post post
        ORDER BY post.createdAt DESC
    """)
    List<Post> findLatestPosts();
}