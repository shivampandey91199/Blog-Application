package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDTO;

public interface CategoryService {

	// create
	CategoryDTO createCategory(CategoryDTO categoryDTO);

	// update
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

	// get one
	CategoryDTO getCategory(Integer categoryId);

	// list-get all
	List<CategoryDTO> getCategories();

	// delete
	void deleteCategory(Integer categoryId);
}
