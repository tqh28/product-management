package com.product.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.product.management.dto.UserAccountUsernameInactiveDate;
import com.product.management.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{

	public UserAccount findByUsername(String username);
	
	public Page<UserAccountUsernameInactiveDate> findByIsLocked(Boolean isLocked, Pageable page);
	
}

