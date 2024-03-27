package com.blog.payloads;


import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	
    private int id;
	
    @NotEmpty
    @Size(min = 3,message = "username must be min of 3 charaters")
	private String name;
    
    @Email(message = "enter valid email")
	private String email;
	
    @NotEmpty
    @Size(min = 3,max = 10,message = "password length should be btw 3-10")
    private String password;
	
    @NotEmpty
    private String about;
    
    private Set<RolesDTO> roles=new HashSet<>();
	
}
