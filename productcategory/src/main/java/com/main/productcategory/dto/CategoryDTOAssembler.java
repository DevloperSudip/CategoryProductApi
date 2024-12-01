package com.main.productcategory.dto;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.main.productcategory.controller.CategoryController;
import com.main.productcategory.model.Category;

@Component
public class CategoryDTOAssembler extends RepresentationModelAssemblerSupport<Category, CategoryDTO> {

    public CategoryDTOAssembler() {
        super(CategoryController.class, CategoryDTO.class);
    }

    @Override
    public CategoryDTO toModel(Category entity) {
        CategoryDTO categoryDTO = new CategoryDTO(entity.getId(), entity.getName());
        categoryDTO.add(Link.of("/api/categories/" + entity.getId()).withSelfRel());  // Add 'self' link
        return categoryDTO;
    }
}