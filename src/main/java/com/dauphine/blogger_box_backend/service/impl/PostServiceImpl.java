package com.dauphine.blogger_box_backend.service.impl;

import com.dauphine.blogger_box_backend.exception.CategoryNotFoundByIdException;
import com.dauphine.blogger_box_backend.exception.PostNotFoundByIdException;
import com.dauphine.blogger_box_backend.models.Category;
import com.dauphine.blogger_box_backend.models.Post;
import com.dauphine.blogger_box_backend.repositories.CategoryRepository;
import com.dauphine.blogger_box_backend.repositories.PostRepository;
import com.dauphine.blogger_box_backend.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllByCategory(UUID categoryId) {
        return postRepository.findByCategory(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Post getById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundByIdException(id));
    }

    @Override
    @Transactional
    public Post create(String title, String content, UUID categoryId) {
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundByIdException(categoryId));
        }

        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setCategory(category);

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post update(UUID id, String title, String content) {
        Post post = getById(id);
        
        if (title != null) {
            post.setTitle(title);
        }
        
        if (content != null) {
            post.setContent(content);
        }
        
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        if (!postRepository.existsById(id)) {
            return false;
        }
        
        postRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getLatestPosts() {
        return postRepository.findLatestPosts();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Post> searchByTitle(String title) {
        return postRepository.findAllLikeTitle(title);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Post> searchByContent(String content) {
        return postRepository.findAllLikeContent(content);
    }
}