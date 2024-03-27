package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.JWTAthuneticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;
import com.blog.security.UserDetailsServiceiml;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS= {
			
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/webjars/**"
	};
	

	@Autowired
	private UserDetailsServiceiml userDetailsServiceiml;

	@Autowired
	private JWTAthuneticationEntryPoint jwtAthuneticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};
	
	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsServiceiml);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
		
	
	 @Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 
		 
		 http.
			csrf()
			.disable()
			.authorizeHttpRequests()
			.antMatchers(PUBLIC_URLS).permitAll()
			.antMatchers(HttpMethod.GET).permitAll()
			.anyRequest()
			.authenticated()
		    .and()
		    .exceptionHandling()
		    .authenticationEntryPoint(this.jwtAthuneticationEntryPoint)
		    .and() 
		    .sessionManagement()
		   .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			  
			  http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
       
			  http.authenticationProvider(daoAuthenticationProvider());
		 
		return http.build();
		
	}
	
	
	
	  @Bean AuthenticationManager
	  authenticationManagerBean(AuthenticationConfiguration c) throws Exception {
	  return c.getAuthenticationManager(); }
	 

	/*
	 * @Bean
	 * 
	 * @Override public AuthenticationManager authenticationManagerBean() throws
	 * Exception { return super.authenticationManagerBean(); }
	 */

	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
		csrf()
		.disable()
		.authorizeHttpRequests()
		.antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers(HttpMethod.GET).permitAll()
		.anyRequest()
		.authenticated()
	    .and()
	    .exceptionHandling()
	    .authenticationEntryPoint(this.jwtAthuneticationEntryPoint)
	    .and() 
	    .sessionManagement()
	   .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		  
		  http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

	*/
	
	/*
	 * @Autowired private JWTAthuneticationEntryPoint jwtAthuneticationEntryPoint;
	 * 
	 * @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;
	 * 
	 * @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();
	 * }
	 * 
	 * @Bean UserDetailsService userDetailsService() { return new
	 * UserDetailsServiceiml(); }
	 * 
	 * @Bean DaoAuthenticationProvider daoAuthProvider() { DaoAuthenticationProvider
	 * daoAuthenticationProvider = new DaoAuthenticationProvider();
	 * daoAuthenticationProvider.setUserDetailsService(userDetailsService());
	 * daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	 * 
	 * return daoAuthenticationProvider; }
	 * 
	 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception {
	 * 
	 * http.csrf().disable() .authorizeHttpRequests().anyRequest().authenticated()
	 * .and() .exceptionHandling().authenticationEntryPoint(this.
	 * jwtAthuneticationEntryPoint) .and() .sessionManagement()
	 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	 * 
	 * http.addFilterBefore(jwtAuthenticationFilter,
	 * UsernamePasswordAuthenticationFilter.class);
	 * 
	 * http.authenticationProvider(daoAuthProvider()); return http.build(); }
	 */
	
	
}
