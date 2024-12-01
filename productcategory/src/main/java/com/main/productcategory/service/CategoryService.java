package com.main.productcategory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.main.productcategory.exception.CategoryNotFoundException;
import com.main.productcategory.model.Category;
import com.main.productcategory.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getAllCategories(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

	/*
	 * public Category getCategoryById(Long id) { return
	 * categoryRepository.findById(id).orElseThrow(() -> new
	 * RuntimeException("Category not found")); }
	 */
    // Get category by ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
    }

    public Category updateCategory(Long id, Category category) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
