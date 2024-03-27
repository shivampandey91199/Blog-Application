package com.blog.services;

import com.blog.payloads.CommentDTO;

public interface CommentService {

	CommentDTO createComment(CommentDTO commentDTO,Integer postId,Integer userId);
	
	CommentDTO getCoomentById(Integer commentId);
	
	void deleteComment(Integer commentID);
}
