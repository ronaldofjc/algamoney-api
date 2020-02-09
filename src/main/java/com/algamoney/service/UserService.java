package com.algamoney.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.algamoney.model.User;
import com.algamoney.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
    private PasswordEncoder encoder;
	
	public User newUser(User user) {
		User lastUser = repository.findTopByOrderByIdDesc();
		user.setId(lastUser.getId() + 1L);
		encodePassword(user);
		return repository.save(user);
	}

	public User update(Long id, User user) {
		User userSaved = findUser(id);
		BeanUtils.copyProperties(user, userSaved, "id");
		encodePassword(userSaved);
		return repository.save(userSaved);
	}
	
	private void encodePassword(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
	}

	private User findUser(Long id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
}
