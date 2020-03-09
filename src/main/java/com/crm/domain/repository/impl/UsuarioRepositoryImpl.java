package com.crm.domain.repository.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.crm.domain.model.Usuario;
import com.crm.domain.model.Usuario_;
import com.crm.domain.repository.query.UsuarioRepositoryQuery;


public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Usuario buscarUsuarioAtivoPeloEmail(String email) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		Root<Usuario> rootFromUsuario = cq.from(Usuario.class);
		Predicate predicates = cb.conjunction();
		predicates = cb.and(cb.equal(rootFromUsuario.get(Usuario_.EMAIL), email),
				     cb.equal(rootFromUsuario.get(Usuario_.ATIVO),true ));
		cq.where(predicates);
		cq.select(rootFromUsuario);
		TypedQuery<Usuario> query = entityManager.createQuery(cq);
		Usuario usuario = query.getSingleResult();
		return usuario;
	}
	

	@Override
	public Optional<Usuario> findUsuarioActiveByEmail(String email){
		
		boolean ativo = true;
		
		TypedQuery<Usuario> query = entityManager
				    .createQuery("SELECT u FROM Usuario u "
				    		   + "WHERE  "
				    		   + "u.email =:email "
				    		   + "and "
				    		   + "u.ativo =:ativo",Usuario.class);
		return query.setParameter("email", email)
			        .setParameter("ativo", ativo) 	
				    .setMaxResults(1)
				    .getResultList()
				    .stream()
				    .findFirst();
	}

	@Override
	public void detached(Usuario usuario) {
		entityManager.detach(usuario);
	}
	

	

}
