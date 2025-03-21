package com.dauphine.blogger_box_backend.service;

import com.dauphine.blogger_box_backend.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAll();

    Category getById(UUID id);

    Category create(String name);

    Category updateName(UUID id, String name);

    boolean deleteById(UUID id);
}