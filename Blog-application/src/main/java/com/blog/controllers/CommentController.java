package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDTO;
import com.blog.services.CommentService;


@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	

	@PostMapping("user/{userId}/post/{postId}/comment")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,@PathVariable Integer userId, @PathVariable Integer postId) {

		CommentDTO commentDTO2 = commentService.createComment(commentDTO, postId,userId);

		return new ResponseEntity<CommentDTO>(commentDTO2, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted successfully", true),HttpStatus.OK);
	}
	
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<CommentDTO> getComment(@PathVariable Integer commentId){
		  
		CommentDTO comentDTO = commentService.getCoomentById(commentId);
		
		return new ResponseEntity<CommentDTO>(comentDTO,HttpStatus.OK);
	}
}
