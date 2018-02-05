package com.product.management.common;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.product.management.entity.UserAccount;
import com.product.management.repository.UserAccountRepository;

public class UserAndRoleManagement {
	
	
	/**
	 * convert from role in number in DB to List<SimpleGrantedAuthority>
	 * @param role
	 * @return List<SimpleGrantedAuthority>
	 */
	public List<SimpleGrantedAuthority> convertAuthority(int role) {
		List<SimpleGrantedAuthority> list = new LinkedList<SimpleGrantedAuthority>();
		
		if ((role & Constant.ROLE_ADMIN_CODE) == Constant.ROLE_ADMIN_CODE) {
			list.add(new SimpleGrantedAuthority(Constant.ROLE_ADMIN_TEXT));
		}
		
		if ((role & Constant.ROLE_USER_CODE) == Constant.ROLE_USER_CODE) {
			list.add(new SimpleGrantedAuthority(Constant.ROLE_USER_TEXT));
		}
		
		return list;
	}
	
	
	/**
	 * get userName of current user
	 * @return String userName
	 */
	public String getCurrentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return authentication.getName();
		}
		return null;
	}
	
	
	/**
	 * get all Authority of current user
	 * @return List<GrantedAuthority>
	 */
	@SuppressWarnings("unchecked")
	public List<GrantedAuthority> getCurrentUserAuthorityList() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return (List<GrantedAuthority>) authentication.getAuthorities();
		}
		return null;
	}
	
	
	/**
	 * check current user is admin to enable edit or insert product
	 * @return boolean
	 */
	public Boolean currentUserIsAdmin() {
		List<GrantedAuthority> authorityList = getCurrentUserAuthorityList();
		if (authorityList != null && !authorityList.isEmpty()
				&& authorityList.contains(new SimpleGrantedAuthority(Constant.ROLE_ADMIN_TEXT))) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * reset attemp count and inactive date of username when login success or enabled by admin
	 * @param username
	 */
	public void resetFailAttempts(UserAccountRepository userAccountRepository, String username) {
		UserAccount account = userAccountRepository.findByUsername(username);
		account.setFailAttempts(0);
		account.setLockedDate(null);
		account.setIsLocked(false);
		
		userAccountRepository.saveAndFlush(account);
	}
	
	
	/**
	 * increase attempt count when login fail
	 * when attempt == 5, inactive account
	 * @param username
	 */
	public Integer increaseFailAttemps(UserAccountRepository userAccountRepository, String username) {
		UserAccount account = userAccountRepository.findByUsername(username);
		if (account == null) {
			return null;
		}
		
		int newAttempt = account.getFailAttempts() + 1;
		if (newAttempt == 5) {
			account.setIsLocked(true);
			account.setLockedDate(new Date());
		}
		account.setFailAttempts(newAttempt);
		userAccountRepository.saveAndFlush(account);
		
		return newAttempt;
	}
}
