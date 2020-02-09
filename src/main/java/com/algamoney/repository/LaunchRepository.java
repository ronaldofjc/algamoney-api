package com.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algamoney.model.Launch;
import com.algamoney.repository.launch.LaunchRepositoryQuery;

public interface LaunchRepository extends JpaRepository<Launch, Long>, LaunchRepositoryQuery {
	
}
