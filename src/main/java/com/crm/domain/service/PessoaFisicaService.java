package com.crm.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crm.domain.model.PessoaFisica;
import com.crm.domain.repository.filtros.PessoaFisicaFiltro;


public interface PessoaFisicaService {

	PessoaFisica save(PessoaFisica pessoaFisica);
	PessoaFisica update(PessoaFisica pessoaFisica);
	void remove(PessoaFisica pessoaFisica);
	List<PessoaFisica> findAll();
	PessoaFisica findPessoaFisicaById(Long id);
	Page<PessoaFisica> listaPessoaComPaginacao(PessoaFisicaFiltro pessoaFisicaFiltro, Pageable pageable);

	/*Optional<PessoaFisica> findPessoaByEmail(String email);*/
}
