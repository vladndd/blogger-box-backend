package com.dauphine.blogger_box_backend.service.impl;

import com.dauphine.blogger_box_backend.models.Category;
import com.dauphine.blogger_box_backend.service.CategoryService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> temporaryCategories;

    public CategoryServiceImpl() {
        temporaryCategories = new ArrayList<>();
        temporaryCategories.add(new Category(UUID.randomUUID(), "my first category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "my second category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "my third category"));
    }

    @Override
    public List<Category> getAll() {
        return new ArrayList<>(temporaryCategories);
    }

    @Override
    public Category getById(UUID id) {
        if (id == null) {
            return null;
        }

        return temporaryCategories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Category create(String name) {
        Category newCategory = new Category(UUID.randomUUID(), name);
        temporaryCategories.add(newCategory);
        return newCategory;
    }

    @Override
    public Category updateName(UUID id, String name) {
        return temporaryCategories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .map(category -> {
                    category.setName(name);
                    return category;
                })
                .orElse(null);
    }

    @Override
    public boolean deleteById(UUID id) {
        return temporaryCategories.removeIf(category -> category.getId().equals(id));
    }
}