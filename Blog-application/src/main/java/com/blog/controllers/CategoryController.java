package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDTO;
import com.blog.services.CategoryService;

import javax.validation.Valid;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService catService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO categoryDTO2 = catService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(categoryDTO2, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable Integer categoryId) {
		CategoryDTO updateCategory = catService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<CategoryDTO>(updateCategory, HttpStatus.OK);
	}

	// single get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable Integer categoryId) {
		CategoryDTO category = catService.getCategory(categoryId);
		return new ResponseEntity<CategoryDTO>(category, HttpStatus.OK);
	}

	// get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getCategories() { 
		
		List<CategoryDTO> categories = catService.getCategories();

		return ResponseEntity.ok(categories);
	}

	// delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		catService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully", true), HttpStatus.OK);
	}
}
