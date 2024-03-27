package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundExceptions;
import com.blog.payloads.PageResponse;
import com.blog.payloads.PostDTO;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceimpl implements PostService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo catRepo;

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("User", "user ID", userId));
		Category category = catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("Category", "category ID", categoryId));

		Post post = modelMapper.map(postDTO, Post.class);
		post.setCategory(category);
		post.setUser(user);
		post.setDate(new Date());
		post.setImage("default.png");

		Post post2 = postRepo.save(post);

		return modelMapper.map(post2, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {

		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("Post", "post id", postId));
		post.setContent(postDTO.getContent());
		post.setTitle(postDTO.getTitle());
		post.setImage(postDTO.getImage());
		post.setDate(new Date());

		Post updatedPost = postRepo.save(post);

		return modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {

		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("Post", "post id", postId));
		postRepo.delete(post);

	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("post", "post ID", postId));

		return modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PageResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
 
		Sort sort=sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> pages = postRepo.findAll(p);

		List<Post> posts = pages.getContent();
		
		List<PostDTO> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		
		PageResponse pg=new PageResponse();
		pg.setContent(postDtos);
		pg.setPageNumber(pages.getNumber());
		pg.setLastPage(pages.isLast());
		pg.setTotalPages(pages.getTotalPages());
		pg.setTotalElements(pages.getTotalElements());
		pg.setPageSize(pages.getSize());
		pg.setFirstPage(pages.isFirst());
		

		return pg;
	}

	
	@Override
	public List<PostDTO> searchPosts(String keyword) {
          List<Post> posts = postRepo.findByTitleContaining(keyword);
           List<PostDTO> postDtos = posts.stream().map((post)-> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postDtos;
	}

	
	@Override
	public List<PostDTO> getAllPostsByUser(Integer userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("User", "userId", userId));

		List<Post> userPosts = postRepo.findByUser(user);

		List<PostDTO> postDTO = userPosts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		return postDTO;
	}

	@Override
	public List<PostDTO> getAllPostsByCategory(Integer categoryId) {

		Category category = catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("Category", "category Id", categoryId));

		List<Post> newcategory = postRepo.findByCategory(category);

		List<PostDTO> categorylistDTO = newcategory.stream().map((cat) -> modelMapper.map(cat, PostDTO.class))
				.collect(Collectors.toList());

		return categorylistDTO;
	}

}
