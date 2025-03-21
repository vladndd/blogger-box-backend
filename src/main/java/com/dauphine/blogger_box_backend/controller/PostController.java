package com.dauphine.blogger_box_backend.controller;

import com.dauphine.blogger_box_backend.dto.CategoryResponse;
import com.dauphine.blogger_box_backend.dto.PostCreationRequest;
import com.dauphine.blogger_box_backend.dto.PostResponse;
import com.dauphine.blogger_box_backend.dto.PostUpdateRequest;
import com.dauphine.blogger_box_backend.models.Post;
import com.dauphine.blogger_box_backend.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(summary = "Get all posts", description = "Returns all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all posts")
    })
    public ResponseEntity<List<PostResponse>> getPosts() {
        List<Post> posts = postService.getAll();
        List<PostResponse> responses = posts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get post by id", description = "Returns post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<PostResponse> getPostById(@PathVariable UUID id) {
        Post post = postService.getById(id);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToResponse(post));
    }

    @PostMapping
    @Operation(summary = "Create post", description = "Creates a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new post"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreationRequest request) {
        Post newPost = postService.create(
                request.getTitle(),
                request.getContent(),
                request.getCategoryId());

        if (newPost == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(newPost));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update post", description = "Updates an existing post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable UUID id,
            @RequestBody PostUpdateRequest request) {

        Post updatedPost = postService.update(
                id,
                request.getTitle(),
                request.getContent());

        if (updatedPost == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToResponse(updatedPost));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post", description = "Deletes an existing post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        boolean deleted = postService.deleteById(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latest")
    @Operation(summary = "Get latest posts", description = "Returns all posts ordered by creation date (newest first)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts ordered by date")
    })
    public ResponseEntity<List<PostResponse>> getLatestPosts() {
        List<Post> latestPosts = postService.getLatestPosts();

        List<PostResponse> responses = latestPosts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get posts by category", description = "Returns all posts for a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by category")
    })
    public ResponseEntity<List<PostResponse>> getPostsByCategory(@PathVariable UUID categoryId) {
        List<Post> posts = postService.getAllByCategory(categoryId);

        List<PostResponse> responses = posts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    private PostResponse convertToResponse(Post post) {
        CategoryResponse categoryResponse = null;

        if (post.getCategory() != null) {
            categoryResponse = new CategoryResponse(
                    post.getCategory().getId(),
                    post.getCategory().getName());
        }

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                categoryResponse);
    }
}