package com.algamoney.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.event.ResourceCreatedEvent;
import com.algamoney.model.User;
import com.algamoney.repository.UserRepository;
import com.algamoney.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('write')")
	public List<User> findAll() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('write')")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		Optional<User> user = repository.findById(id);
		return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_USUARIO') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_USUARIO') and #oauth2.hasScope('write')")
	public ResponseEntity<User> save(@Valid @RequestBody User user, HttpServletResponse response) {
		User userSaved = userService.newUser(user);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, user.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('write')")
	public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user) {
		return ResponseEntity.ok(userService.update(id, user));
	}
}
