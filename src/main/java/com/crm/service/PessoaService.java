package com.crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crm.model.Pessoa;
import com.crm.repository.filtros.PessoaFiltro;

public interface PessoaService {
	
	Pessoa save(Pessoa pessoa);
	Pessoa update(Pessoa pessoa);
	void remove(Pessoa pessoa);
	List<Pessoa> findAll();
	Pessoa findPessoaById(Long id);
	
	
	List<Pessoa> findPessoaByName(String nome);
	
	
	Page<Pessoa> listaPessoaComPaginacao(PessoaFiltro pessoaFiltro, Pageable pageable);
	Optional<Pessoa> findPessoaByEmail(String email);
	
}
