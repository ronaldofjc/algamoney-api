package com.algamoney.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.algamoney.model.User;

public class UserSystem extends org.springframework.security.core.userdetails.User {
	
	private static final long serialVersionUID = 1L;

	private User user;
	
	public UserSystem(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}	
}
