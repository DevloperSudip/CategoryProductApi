package com.main.productcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.productcategory.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
