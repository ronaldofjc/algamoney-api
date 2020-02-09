package com.algamoney.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algamoney.model.Person;
import com.algamoney.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository repository;
	
	public Person update(Long id, Person person) {
		Person personSaved = findPerson(id);
		BeanUtils.copyProperties(person, personSaved, "id");
		return repository.save(personSaved);
	}

	public void updatePropertieActive(Long id, Boolean active) {
		Person personSaved = findPerson(id);
		personSaved.setActive(active);
		repository.save(personSaved);
	}
	
	public Person findPerson(Long id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
}
