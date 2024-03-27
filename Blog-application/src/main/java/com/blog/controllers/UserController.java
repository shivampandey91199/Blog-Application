package com.blog.controllers;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDTO;
import com.blog.services.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// create user
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {

		UserDTO createdUser = userService.createUser(userDTO);

		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

	}

	// update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUSer(@Valid @RequestBody UserDTO userDTO, @PathVariable Integer userId) {
		UserDTO updateUser = userService.updateUser(userDTO, userId);
		return ResponseEntity.ok(updateUser);
	}

	// get list of user
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUser());
	}

	// get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}
    //admin can only delete user
	// delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
			
		userService.deleteUser(userId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true), HttpStatus.OK);
	}
}
