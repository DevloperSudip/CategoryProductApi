package com.main.productcategory.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.main.productcategory.dto.ProductDTO;
import com.main.productcategory.model.Category;
import com.main.productcategory.model.Product;
import com.main.productcategory.repository.CategoryRepository;
import com.main.productcategory.repository.ProductRepository;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all products with pagination
    public Page<Product> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    // Create a new product from ProductDTO
    public Product createProduct(ProductDTO productDTO) {
        // Convert DTO to entity
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        // Fetch category by ID if provided in the DTO
        if (productDTO.getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
            // Set category if exists, otherwise set null
            product.setCategory(category.orElse(null));
        }

        return productRepository.save(product);
    }

    // Get a product by its ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Update an existing product
    public Product updateProduct(Long id, ProductDTO productDTO) {
        // Fetch existing product by ID
        Product existingProduct = getProductById(id);
        
        // Update product fields with the DTO values
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());

        // Update the category, if the categoryId is provided
        if (productDTO.getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
            existingProduct.setCategory(category.orElse(null));  // Set to null if no category found
        }

        return productRepository.save(existingProduct);
    }

    // Delete a product by its ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
