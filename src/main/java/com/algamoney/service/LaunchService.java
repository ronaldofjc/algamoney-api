package com.algamoney.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algamoney.model.Launch;
import com.algamoney.model.Person;
import com.algamoney.repository.LaunchRepository;
import com.algamoney.repository.PersonRepository;
import com.algamoney.service.exception.PersonInactiveException;
import com.algamoney.service.exception.PersonInexistentException;

@Service
public class LaunchService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private LaunchRepository launchRepository;

	public Launch save(Launch launch) {
		if (launch == null || launch.getPerson() == null || launch.getPerson().getId() == null) {
			throw new PersonInexistentException();
		}
		
		Optional<Person> person = personRepository.findById(launch.getPerson().getId());
		
		if (person.isPresent() && person.get().isInactive()) {
			throw new PersonInactiveException();
		}
		
		return launchRepository.save(launch);
	}

}
