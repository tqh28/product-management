package com.product.management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.product.management.common.UserAndRoleManagement;

@Configuration
public class BeanDeclare {

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserAndRoleManagement getUserAndRoleManagement() {
		return new UserAndRoleManagement();
	}
	
}
