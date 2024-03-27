package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstant;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundExceptions;
import com.blog.payloads.UserDTO;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceimpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDTO createUser(UserDTO userDTO) {

		User user = this.dtoToUser(userDTO);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundExceptions("user", "id", userId));

		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());

		this.userRepo.save(user);

		return this.userToDto(user);
	}

	@Override
	public UserDTO getUserById(Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundExceptions("user", "id", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUser() {

		List<User> users = userRepo.findAll();
		List<UserDTO> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundExceptions("user", "id", userId));
		this.userRepo.delete(user);

	}

	public User dtoToUser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);

		return user;
	}

	public UserDTO userToDto(User user) {
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

		return userDTO;
	}

	
	  @Override public UserDTO registerUserDTO(UserDTO userDTO) { User user =
	  this.modelMapper.map(userDTO, User.class);
	  
	  // encoded the password
	  user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	  
	  // roles
	  Role role = roleRepo.findById(AppConstant.NORMAL_ROLE).get();
	  user.getRoles().add(role);
	  
	  User savedUser = userRepo.save(user); return modelMapper.map(savedUser,
	  UserDTO.class); }
	 
}
