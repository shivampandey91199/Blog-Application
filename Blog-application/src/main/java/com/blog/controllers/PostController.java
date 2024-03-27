package com.blog.controllers;


import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstant;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PageResponse;
import com.blog.payloads.PostDTO;
import com.blog.services.FileService;
import com.blog.services.PostService;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create post
	@PostMapping("/user/{userId}/category/{categoryId}/")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDTO createPost = postService.createPost(postDTO, userId, categoryId);

		return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);
	}

	// all posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getAllUserPosts(@PathVariable Integer userId) {

		List<PostDTO> postsByUser = postService.getAllPostsByUser(userId);

		return new ResponseEntity<List<PostDTO>>(postsByUser, HttpStatus.OK);
	}

	// all posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getAllCategoryPosts(@PathVariable Integer categoryId) {

		List<PostDTO> postsByCategory = postService.getAllPostsByCategory(categoryId);

		return new ResponseEntity<List<PostDTO>>(postsByCategory, HttpStatus.OK);
	}

	// all posts
	@GetMapping("/posts")
	public ResponseEntity<PageResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

		PageResponse pageResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

		return new ResponseEntity<PageResponse>(pageResponse, HttpStatus.OK);
	}

	//  post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {

		PostDTO postDTO = postService.getPostById(postId);

		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
	}

	// delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {

		postService.deletePost(postId);

		return new ApiResponse("Post deleted successfully", true);
	}

	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId) {

		PostDTO postDTO2 = postService.updatePost(postDTO, postId);

		return new ResponseEntity<PostDTO>(postDTO2, HttpStatus.OK);
	}

	// search
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keyword) {
		List<PostDTO> searchPosts = postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDTO>>(searchPosts, HttpStatus.OK);
	}

	// image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadImage(@RequestParam MultipartFile image, @PathVariable Integer postId)
			throws Exception {

		PostDTO postById = postService.getPostById(postId);
		String uploadImage = this.fileService.uploadImage(path, image);

		postById.setImage(uploadImage);
		PostDTO postDTO = postService.updatePost(postById, postId);

		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
	}

	// method to serve files
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, 
			HttpServletResponse response) throws Throwable {

		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
