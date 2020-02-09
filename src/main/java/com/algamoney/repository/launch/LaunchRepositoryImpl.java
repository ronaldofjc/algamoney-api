package com.algamoney.repository.launch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.algamoney.model.Launch;
import com.algamoney.repository.filter.LaunchFilter;
import com.algamoney.repository.projection.LaunchResume;

public class LaunchRepositoryImpl implements LaunchRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Launch> filter(LaunchFilter launchFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Launch> criteria = builder.createQuery(Launch.class);
		Root<Launch> root = criteria.from(Launch.class);
		
		Predicate[] predicates = createRestrictions(launchFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Launch> query = manager.createQuery(criteria);
		
		addPageRestrictions(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(launchFilter));
	}
	
	@Override
	public Page<LaunchResume> resume(LaunchFilter launchFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LaunchResume> criteria = builder.createQuery(LaunchResume.class);
		Root<Launch> root = criteria.from(Launch.class);
		
		criteria.select(builder.construct(LaunchResume.class
				, root.get("id"), root.get("description"), root.get("dueDate"), root.get("paymentDate")
				, root.get("value"), root.get("type"), root.get("category").get("name")
				, root.get("person").get("name")));
		
		Predicate[] predicates = createRestrictions(launchFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<LaunchResume> query = manager.createQuery(criteria);
		
		addPageRestrictions(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(launchFilter));
	}

	private Predicate[] createRestrictions(LaunchFilter launchFilter, CriteriaBuilder builder, Root<?> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(launchFilter.getDescription())) {
			predicates.add(builder.like(builder.lower(
					root.get("description")), "%" + launchFilter.getDescription().toLowerCase() + "%"));
		}
		
		if (launchFilter.getInitialDueDate() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dueDate"), launchFilter.getInitialDueDate()));
		}
		
		if (launchFilter.getEndDueDate() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dueDate"), launchFilter.getEndDueDate()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private Long total(LaunchFilter launchFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Launch> root = criteria.from(Launch.class);
		
		Predicate[] predicates = createRestrictions(launchFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

	private void addPageRestrictions(TypedQuery<?> query, Pageable pageable) {
		int atualPage = pageable.getPageNumber();
		int totalItensForPage = pageable.getPageSize();
		int firstItemOfPage = atualPage * totalItensForPage;
		
		query.setFirstResult(firstItemOfPage);
		query.setMaxResults(totalItensForPage);
	}
}
