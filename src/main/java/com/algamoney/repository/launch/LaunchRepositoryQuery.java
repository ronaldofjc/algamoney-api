package com.algamoney.repository.launch;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algamoney.model.Launch;
import com.algamoney.repository.filter.LaunchFilter;
import com.algamoney.repository.projection.LaunchResume;

public interface LaunchRepositoryQuery {
	
	public Page<Launch> filter(LaunchFilter launchFilter, Pageable pageable);
	public Page<LaunchResume> resume(LaunchFilter launchFilter, Pageable pageable);

}
