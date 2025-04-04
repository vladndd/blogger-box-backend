package com.dauphine.blogger_box_backend.exception;

import java.util.UUID;

public class PostNotFoundByIdException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private final UUID postId;

    public PostNotFoundByIdException(UUID id) {
        super("Post not found with id: " + id);
        this.postId = id;
    }
    
    public UUID getPostId() {
        return postId;
    }
}