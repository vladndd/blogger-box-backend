package com.dauphine.blogger_box_backend.controller;

import com.dauphine.blogger_box_backend.dto.CategoryCreationRequest;
import com.dauphine.blogger_box_backend.dto.CategoryResponse;
import com.dauphine.blogger_box_backend.dto.CategoryUpdateRequest;
import com.dauphine.blogger_box_backend.models.Category;
import com.dauphine.blogger_box_backend.service.CategoryService;

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
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Returns all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories")
    })
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<Category> categories = categoryService.getAll();

        List<CategoryResponse> responses = categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id", description = "Returns category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the category"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable UUID id) {
        Category category = categoryService.getById(id);

        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        CategoryResponse response = new CategoryResponse(category.getId(), category.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create category", description = "Creates a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new category")
    })
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryCreationRequest request) {
        Category newCategory = categoryService.create(request.getName());

        CategoryResponse response = new CategoryResponse(newCategory.getId(), newCategory.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Updates an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the category"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable UUID id,
            @RequestBody CategoryUpdateRequest request) {

        Category updatedCategory = categoryService.updateName(id, request.getName());

        if (updatedCategory == null) {
            return ResponseEntity.notFound().build();
        }

        CategoryResponse response = new CategoryResponse(
                updatedCategory.getId(),
                updatedCategory.getName());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Deletes an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the category"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        boolean deleted = categoryService.deleteById(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}