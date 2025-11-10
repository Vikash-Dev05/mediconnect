package com.mediconnect.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mediconnect.entity.Category;
import com.mediconnect.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. ADD CATEGORY (e.g., Cardiologist)
    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // 2. GET ALL CATEGORIES
    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 3. DELETE CATEGORY
    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "Category deleted with id: " + id;
    }
}