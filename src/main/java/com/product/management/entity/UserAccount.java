package com.product.management.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserAccount {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "role", nullable = false)
	private Integer role;
	
	@Column(name = "is_locked", nullable = false)
	private Boolean isLocked;
	
	@Column(name = "fail_attempts")
	private Integer failAttempts;
	
	@Column(name = "locked_date")
	private Date lockedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getFailAttempts() {
		return failAttempts;
	}

	public void setFailAttempts(Integer failAttempts) {
		this.failAttempts = failAttempts;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

}
