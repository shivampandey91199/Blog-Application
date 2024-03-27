package com.blog.services;

import java.util.List;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.payloads.PageResponse;
import com.blog.payloads.PostDTO;

public interface PostService {

	//create post
	PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId);
	
	//update post
	PostDTO updatePost(PostDTO postDTO,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get
	PostDTO getPostById(Integer postId);
	
	//getALl
	PageResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//getAll  post by user
	List<PostDTO> getAllPostsByUser(Integer userId);
	
	//getAll  post by user
	List<PostDTO> getAllPostsByCategory(Integer categoryId);
	
	//search post
	List<PostDTO> searchPosts(String keyword);
}
