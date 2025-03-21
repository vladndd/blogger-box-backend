package com.dauphine.blogger_box_backend.service.impl;

import com.dauphine.blogger_box_backend.models.Category;
import com.dauphine.blogger_box_backend.models.Post;
import com.dauphine.blogger_box_backend.service.CategoryService;
import com.dauphine.blogger_box_backend.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final List<Post> temporaryPosts;
    private final CategoryService categoryService;

    @Autowired
    public PostServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;

        temporaryPosts = new ArrayList<>();
        Category category1 = categoryService.getById(
                categoryService.getAll().isEmpty() ? null : categoryService.getAll().get(0).getId());

        temporaryPosts.add(new Post(UUID.randomUUID(), "my first post", "my first post content",
                LocalDateTime.now().minusDays(2), category1));
        temporaryPosts.add(new Post(UUID.randomUUID(), "my second post", "my second post content",
                LocalDateTime.now().minusDays(1), category1));

        if (categoryService.getAll().size() > 1) {
            Category category2 = categoryService.getById(categoryService.getAll().get(1).getId());
            temporaryPosts.add(new Post(UUID.randomUUID(), "my third post", "my third post content",
                    LocalDateTime.now(), category2));
        }
    }

    @Override
    public List<Post> getAllByCategory(UUID categoryId) {
        return temporaryPosts.stream()
                .filter(post -> post.getCategory() != null && post.getCategory().getId().equals(categoryId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getAll() {
        return new ArrayList<>(temporaryPosts);
    }

    @Override
    public Post getById(UUID id) {
        return temporaryPosts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Post create(String title, String content, UUID categoryId) {
        Category category = null;
        if (categoryId != null) {
            category = categoryService.getById(categoryId);
            if (category == null) {
                return null;
            }
        }

        Post newPost = new Post(
                UUID.randomUUID(),
                title,
                content,
                LocalDateTime.now(),
                category);

        temporaryPosts.add(newPost);
        return newPost;
    }

    @Override
    public Post update(UUID id, String title, String content) {
        for (int i = 0; i < temporaryPosts.size(); i++) {
            Post post = temporaryPosts.get(i);
            if (post.getId().equals(id)) {
                Post updated = new Post(
                        id,
                        title != null ? title : post.getTitle(),
                        content != null ? content : post.getContent(),
                        post.getCreatedAt(),
                        post.getCategory());

                temporaryPosts.set(i, updated);
                return updated;
            }
        }

        return null;
    }

    @Override
    public boolean deleteById(UUID id) {
        return temporaryPosts.removeIf(post -> post.getId().equals(id));
    }

    public List<Post> getLatestPosts() {
        List<Post> sortedPosts = new ArrayList<>(temporaryPosts);
        sortedPosts.sort((post1, post2) -> {
            if (post1.getCreatedAt() == null)
                return 1;
            if (post2.getCreatedAt() == null)
                return -1;
            return post2.getCreatedAt().compareTo(post1.getCreatedAt());
        });

        return sortedPosts;
    }
}