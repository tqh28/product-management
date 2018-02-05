package com.product.management.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.product.management.common.UserAndRoleManagement;
import com.product.management.repository.UserAccountRepository;

@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserAndRoleManagement userAndRoleManagement;
	
	@Autowired
	@Qualifier("userDetailService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			Authentication auth = super.authenticate(authentication);
			// login success, reset fail attempts
			userAndRoleManagement.resetFailAttempts(userAccountRepository, authentication.getName());
			
			return auth;
		} catch (DisabledException e) {
			String error = "<strong>Account: " + authentication.getName() + " is disabled!</strong>"
					+ "<p>Please contact Admin to enable.</p>";
			throw new DisabledException(error);
			
		} catch (BadCredentialsException e) {
			String error = null;
			
			if (authentication.getName().equals("admin")) {
				error = "<strong>Username or Password is wrong. Please check again.</strong>";
			} else {
				//invalid login, update to user fail attempts
				Integer newFailAttemps = userAndRoleManagement.increaseFailAttemps(
						userAccountRepository, authentication.getName());
				
				if (newFailAttemps == null) {
					error = "<strong>Account: " + authentication.getName() + " is not exsited!</strong>";
				} else if (newFailAttemps < 5) {
					error = "<strong>Username or Password is wrong. Please check again.</strong>"
							+ "<p>Account will be disabled after " + (5 - newFailAttemps) + " time(s).</p>";
				} else {
					error = "<strong>Account: " + authentication.getName() + " is disabled!</strong>"
							+ "<p>Please contact Admin to enable.</p>";
				}
			}
			
			// TODO
			// using DisabledException instead of BadCredentialsException to bring error message to controller
			throw new DisabledException(error);
		}
	}
	
}