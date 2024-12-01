package com.main.productcategory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main.productcategory.dto.CategoryDTO;
import com.main.productcategory.dto.CategoryDTOAssembler;
import com.main.productcategory.exception.CategoryNotFoundException;
import com.main.productcategory.model.Category;
import com.main.productcategory.repository.CategoryRepository;
import com.main.productcategory.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get all categories with pagination
	/*
	 * @GetMapping public Page<Category> getAllCategories(@RequestParam int page) {
	 * return categoryService.getAllCategories(page, 10); // 10 is page size }
	 */
    @Autowired
     private CategoryRepository categoryRepository;
		/*
		 * @GetMapping public Page<Category> getCategories(@RequestParam(defaultValue =
		 * "0") int page,
		 * 
		 * @RequestParam(defaultValue = "10") int size) { Pageable pageable =
		 * PageRequest.of(page, size); return categoryRepository.findAll(pageable); }
		 */

    
    @Autowired
    private PagedResourcesAssembler<Category> pagedResourcesAssembler;

    @Autowired
    private CategoryDTOAssembler categoryDTOAssembler;

    // GET /api/categories?page=0&size=5
    @GetMapping
    public PagedModel<CategoryDTO> getCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(categories, categoryDTOAssembler);  // Convert page to PagedModel with links
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // Exception handler for CategoryNotFoundException
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFound(CategoryNotFoundException ex) {
        // Return 404 with custom message
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    // Create a new category
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

	/*
	 * // Get category by ID
	 * 
	 * @GetMapping("/{id}") public Category getCategoryById(@PathVariable Long id) {
	 * return categoryService.getCategoryById(id); }
	 */

    // Update category by ID
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    // Delete category by ID
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}