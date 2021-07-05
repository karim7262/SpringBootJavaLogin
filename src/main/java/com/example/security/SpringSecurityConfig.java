package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth.userDetailsService(userDetailsService);
 	}
	
	@Override
 	protected void configure(HttpSecurity http) throws Exception {
 		http.authorizeRequests()
 		.antMatchers("/admin*").hasRole("ADMIN")
 		.antMatchers("/managers*").hasAnyRole("MANAGER","ADMIN")
 		.antMatchers("/securities*").hasAnyRole("ADMIN","SECURITY")
 		.antMatchers("/customers*").hasAnyRole("ADMIN","CUSTOMER")
 		.antMatchers("/*").permitAll()							//With or Without Auth
 		.and().formLogin();	
 	}
	
	@Bean 
	public PasswordEncoder getPasswordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
}

