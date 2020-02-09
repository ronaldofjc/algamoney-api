package com.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algamoney.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
