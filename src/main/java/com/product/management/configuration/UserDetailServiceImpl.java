package com.product.management.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.product.management.common.UserAndRoleManagement;
import com.product.management.entity.UserAccount;
import com.product.management.repository.UserAccountRepository;

@Component(value = "userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserAndRoleManagement userAndRoleManagement;
	

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserAccount user = userAccountRepository.findByUsername(userName);
		if(user == null) {
			throw new UsernameNotFoundException("Account is not existed.");
		}
		
//		User(String username, String password, boolean enabled,
//				boolean accountNonExpired, boolean credentialsNonExpired,
//				boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
		return new User(user.getUsername(), user.getPassword(), ! user.getIsLocked(),
				true, true, true, userAndRoleManagement.convertAuthority(user.getRole()));
	}
	
	

}
