package com.dauphine.blogger_box_backend.service.impl;

import com.dauphine.blogger_box_backend.exception.CategoryNotFoundByIdException;
import com.dauphine.blogger_box_backend.exception.DuplicateCategoryException;
import com.dauphine.blogger_box_backend.models.Category;
import com.dauphine.blogger_box_backend.repositories.CategoryRepository;
import com.dauphine.blogger_box_backend.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Category getById(UUID id) {
        if (id == null) {
            return null;
        }
        
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundByIdException(id));
    }

    @Override
    @Transactional
    public Category create(String name) {
        // Check if a category with the given name already exists
        Category existingCategory = categoryRepository.findByNameIgnoreCase(name);
        if (existingCategory != null) {
            throw new DuplicateCategoryException(name);
        }
        
        Category newCategory = new Category();
        newCategory.setName(name);
        return categoryRepository.save(newCategory);
    }

    @Override
    @Transactional
    public Category updateName(UUID id, String name) {
        Category category = getById(id);
        
        // Check if there's already a different category with the new name
        Category existingCategory = categoryRepository.findByNameIgnoreCase(name);
        if (existingCategory != null && !existingCategory.getId().equals(id)) {
            throw new DuplicateCategoryException(name);
        }
        
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        if (!categoryRepository.existsById(id)) {
            return false;
        }
        
        categoryRepository.deleteById(id);
        return true;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Category> searchByName(String name) {
        return categoryRepository.findAllLikeName(name);
    }
}