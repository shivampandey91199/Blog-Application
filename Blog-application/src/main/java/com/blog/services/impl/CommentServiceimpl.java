package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundExceptions;
import com.blog.payloads.CommentDTO;
import com.blog.repositories.Commentrepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.CommentService;

@Service
public class CommentServiceimpl  implements CommentService{

	@Autowired
	private Commentrepo commentrepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId,Integer userId) {

		User user= userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundExceptions("user", "userId", userId));
	
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundExceptions("Post", "postId", postId));
		
		Comment comment = modelmapper.map(commentDTO, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		Comment commentnew = commentrepo.save(comment);
		
		return modelmapper.map(commentnew,CommentDTO.class);
	}
	
	

	@Override
	public void deleteComment(Integer commentID) {

		Comment comment = commentrepo.findById(commentID).orElseThrow(()->new ResourceNotFoundExceptions("Comment" , "commentId", commentID));
		commentrepo.delete(comment);
		
		
	}



	@Override
	public CommentDTO getCoomentById(Integer commentId) {
		 Comment comment = commentrepo.findById(commentId).orElseThrow(()->new ResourceNotFoundExceptions("Comment", "commentId", commentId));
	
		 return modelmapper.map(comment, CommentDTO.class);
	}

}
