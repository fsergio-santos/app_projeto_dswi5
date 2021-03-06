package com.crm.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.domain.model.Pessoa;
import com.crm.domain.repository.query.PessoaQuery;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaQuery{

	@Query(value="SELECT p FROM Pessoa p WHERE p.nome like %:nome%")
	List<Pessoa> findPessoaByName(@Param("nome") String nome);
	
}
