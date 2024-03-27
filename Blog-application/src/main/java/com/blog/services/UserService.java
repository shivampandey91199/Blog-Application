package com.blog.services;

import java.util.List;


import com.blog.payloads.UserDTO;

public interface UserService {
 
	//register user
	
	  UserDTO registerUserDTO(UserDTO userDTO);
	 
	//create user
	 UserDTO createUser(UserDTO user);
	
	 //update user
	 UserDTO updateUser(UserDTO user,Integer userId);
	 
	 //get single user
	 UserDTO getUserById(Integer userId);
	 
	 //get all the users
	 List<UserDTO> getAllUser();
	 
	 //delete user
	 void deleteUser(Integer userId);
}
