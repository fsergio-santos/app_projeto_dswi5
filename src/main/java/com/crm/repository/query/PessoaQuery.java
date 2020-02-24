package com.crm.repository.query;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crm.model.Pessoa;
import com.crm.repository.filtros.PessoaFiltro;

public interface PessoaQuery {

	Page<Pessoa> listaPessoaComPaginacao(PessoaFiltro pessoaFiltro, Pageable pageable);

	Optional<Pessoa> findPessoaByEmail(String email);
	
}
