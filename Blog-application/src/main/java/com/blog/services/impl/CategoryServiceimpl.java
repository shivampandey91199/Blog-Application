package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundExceptions;
import com.blog.payloads.CategoryDTO;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceimpl implements CategoryService {

	@Autowired
	private CategoryRepo catRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {

		Category category = this.modelMapper.map(categoryDTO, Category.class);
		Category category2 = catRepo.save(category);
		return modelMapper.map(category2, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {

		Category category = catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("Category", "category id", categoryId));
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category category2 = catRepo.save(category);
		return modelMapper.map(category2, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category category = catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("Category", "category id", categoryId));
		return modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getCategories() {
		List<Category> categories = catRepo.findAll();
		List<CategoryDTO> catDto = categories.stream().map((cat) -> modelMapper.map(cat, CategoryDTO.class))
				                                 .collect(Collectors.toList());
		return catDto;
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category category = catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("Category", "category id", categoryId));
		catRepo.delete(category);
	}

}
