package com.algamoney.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algamoney.event.ResourceCreatedEvent;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {
	
	@Override
	public void onApplicationEvent(ResourceCreatedEvent event) {
		HttpServletResponse response = event.getResponse();
		Long code = event.getCode();
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(code).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}	
}
