package com.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.entities.Category;
import com.blog.entities.Comment;
import com.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String image;
	
	private Date date;
	
	private UserDTO user;
	
	private CategoryDTO category;
	
	private List<CommentDTO> comments=new ArrayList<>();
	
	
}
