package com.algamoney.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LaunchFilter {
	
	private String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate initialDueDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDueDate;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getInitialDueDate() {
		return initialDueDate;
	}
	public void setInitialDueDate(LocalDate initialDueDate) {
		this.initialDueDate = initialDueDate;
	}
	public LocalDate getEndDueDate() {
		return endDueDate;
	}
	public void setEndDueDate(LocalDate endDueDate) {
		this.endDueDate = endDueDate;
	}
}
