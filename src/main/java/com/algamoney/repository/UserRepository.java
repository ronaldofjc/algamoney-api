package com.algamoney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algamoney.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);
	
	public User findTopByOrderByIdDesc();
}
