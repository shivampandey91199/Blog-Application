package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundExceptions;
import com.blog.repositories.UserRepo;

@Service
public class UserDetailsServiceiml implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
        		.orElseThrow(()->new ResourceNotFoundExceptions("User", "Email"+username, 0));
           	System.out.println("user: "+user );
		return user;
	}

}
