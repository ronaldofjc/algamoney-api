package com.algamoney.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.event.ResourceCreatedEvent;
import com.algamoney.exception.AlgaMoneyExceptionHandler.Error;
import com.algamoney.model.Launch;
import com.algamoney.repository.LaunchRepository;
import com.algamoney.repository.filter.LaunchFilter;
import com.algamoney.repository.projection.LaunchResume;
import com.algamoney.service.LaunchService;
import com.algamoney.service.exception.PersonInactiveException;
import com.algamoney.service.exception.PersonInexistentException;

@RestController
@RequestMapping("/releases")
public class LaunchController {
	
	@Autowired
	private LaunchRepository repository;
	
	@Autowired
	private LaunchService launchService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Launch> search(LaunchFilter launchFilter, Pageable pageable) {
		return repository.filter(launchFilter, pageable);
	}
	
	@GetMapping(params = "resume")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<LaunchResume> resume(LaunchFilter launchFilter, Pageable pageable) {
		return repository.resume(launchFilter, pageable);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Launch> findById(@PathVariable Long id) {
		Optional<Launch> launchSaved = repository.findById(id);
		return launchSaved.isPresent() ? ResponseEntity.ok(launchSaved.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Launch> save(@Valid @RequestBody Launch launch, HttpServletResponse response) {
		Launch launchSaved = launchService.save(launch);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, launchSaved.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(launchSaved);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remove(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@ExceptionHandler({ PersonInactiveException.class })
	public ResponseEntity<Object> handlePersonInactiveException(PersonInactiveException ex) {
		String messageUser = messageSource.getMessage("person.inactive", null, LocaleContextHolder.getLocale());
		String messageDeveloper = ex.toString();
		List<Error> errors = Arrays.asList(new Error(messageUser, messageDeveloper));
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler({ PersonInexistentException.class })
	public ResponseEntity<Object> handlePersonInexistentException(PersonInexistentException ex) {
		String messageUser = messageSource.getMessage("person.inexistent", null, LocaleContextHolder.getLocale());
		String messageDeveloper = ex.toString();
		List<Error> errors = Arrays.asList(new Error(messageUser, messageDeveloper));
		return ResponseEntity.badRequest().body(errors);
	}
	
}
