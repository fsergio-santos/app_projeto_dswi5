package com.crm.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.crm.model.Pessoa;
import com.crm.model.Pessoa_;
import com.crm.repository.filtros.PessoaFiltro;
import com.crm.repository.query.PessoaQuery;

public class PessoaRepositoryImpl implements PessoaQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Page<Pessoa> listaPessoaComPaginacao(PessoaFiltro pessoaFiltro, Pageable pageable) {

		List<Pessoa> listaPessoa = new ArrayList<>();
		List<Predicate> listaPredicados = new ArrayList<>();
		TypedQuery<Pessoa> query = null;
		
	    int totalRegistroPorPagina = pageable.getPageSize(); 
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistroPorPagina;
			
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
		Root<Pessoa> rootFromPessoa = criteriaQuery.from(Pessoa.class);
		
		if (!StringUtils.isEmpty(pessoaFiltro.getNome())) {
			listaPredicados.add(criteriaBuilder.like(criteriaBuilder.lower(rootFromPessoa.get(Pessoa_.NOME)),"%"+pessoaFiltro.getNome()+"%"));
		}
		
		
		if (listaPredicados.size() != -1) {
			criteriaQuery.where(criteriaBuilder.and(listaPredicados.toArray(new Predicate[listaPredicados.size()])));
			query = entityManager.createQuery(criteriaQuery);   
		} else  {
			query = entityManager.createQuery(criteriaQuery);
		}
		
		
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistroPorPagina);
		listaPessoa = query.getResultList();
		return new PageImpl<>(listaPessoa,pageable,totalRegisto(listaPredicados));
	}
	

	private Long totalRegisto(List<Predicate> listaPredicados) {
		TypedQuery<Long> query = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Pessoa> rootFromPessoa = criteriaQuery.from(Pessoa.class);
		criteriaQuery.select(criteriaBuilder.count(rootFromPessoa));
		if (listaPredicados.size() != -1) {
			criteriaQuery.where(criteriaBuilder.and(listaPredicados.toArray(new Predicate[listaPredicados.size()])));
			query = entityManager.createQuery(criteriaQuery);   
		} else  {
			query = entityManager.createQuery(criteriaQuery);
		}
		Long result = query.getSingleResult();
	    return result;
	}

	@Override
	public Optional<Pessoa> findPessoaByEmail(String email) {
		TypedQuery<Pessoa> query = entityManager
				.createQuery("SELECT p FROM Pessoa p WHERE p.email =:email",Pessoa.class);
		return query.setParameter("email", email)
				    .setMaxResults(1)
				    .getResultList()
				    .stream()
				    .findFirst();
	}

}
