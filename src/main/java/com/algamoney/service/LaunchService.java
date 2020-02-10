package com.algamoney.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	
	public Launch update(Long id, Launch launch) {
		Launch launchSaved = findLaunch(id);
		
		if (!launchSaved.getPerson().getName().equals(launch.getPerson().getName())) {
			validatePerson(launch);
		}
		
		BeanUtils.copyProperties(launch, launchSaved, "id");
		return launchRepository.save(launchSaved);
	}

	public void validatePerson(Launch launch) {
		personRepository.findById(launch.getPerson().getId()).orElseThrow(() -> new PersonInexistentException());
	}

	public Launch findLaunch(Long id) {
		return launchRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

}
