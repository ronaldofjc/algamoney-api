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
import com.algamoney.model.Person;
import com.algamoney.repository.PersonRepository;
import com.algamoney.service.PersonService;

@RestController
@RequestMapping("/peoples")
public class PersonController {
	
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public List<Person> getAll() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Person> findById(@PathVariable Long id) {
		Optional<Person> person = repository.findById(id);
		return person.isPresent() ? ResponseEntity.ok(person.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Person> save(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person personSaved = repository.save(person);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, person.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void remove(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Person> update(@PathVariable Long id, @Valid @RequestBody Person person) {
		return ResponseEntity.ok(personService.update(id, person));
	}
	
	@PutMapping("/{id}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PESSOA') and #oauth2.hasScope('write')")
	public void updatePropertieActive(@PathVariable Long id, @RequestBody Boolean active) {
		personService.updatePropertieActive(id, active);
	}
}






