package com.blog;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstant;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("kcew"));
		System.out.println(this.passwordEncoder.encode("abc"));

		try {
			Role role1 = new Role();
			role1.setId(AppConstant.ADMIN_ROLE);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(AppConstant.NORMAL_ROLE);
			role2.setName("ROLE_NORMAL");
		
			roleRepo.save(role1);
			roleRepo.save(role2);
			
		}

			/*List<Role> roles = List.of(role, role1);

			List<Role> listRole = roleRepo.saveAll(roles);

			listRole.forEach(r -> {
				System.out.println(r.getName());*/
				
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
