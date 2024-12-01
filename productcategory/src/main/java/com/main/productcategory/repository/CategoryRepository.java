package com.main.productcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.productcategory.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
