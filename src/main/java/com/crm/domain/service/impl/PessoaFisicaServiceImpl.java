package com.crm.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.domain.model.PessoaFisica;
import com.crm.domain.repository.PessoaFisicaRepository;
import com.crm.domain.repository.filtros.PessoaFisicaFiltro;
import com.crm.domain.service.PessoaFisicaService;

@Service
@Transactional
public class PessoaFisicaServiceImpl implements PessoaFisicaService {

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Override
	public PessoaFisica save(PessoaFisica pessoaFisica) {
		return pessoaFisicaRepository.save(pessoaFisica);
	}

	@Override
	public PessoaFisica update(PessoaFisica pessoaFisica) {
		return pessoaFisicaRepository.save(pessoaFisica);
	}

	@Override
	public void remove(PessoaFisica pessoaFisica) {
	     PessoaFisica pessoaFisicaEncontrada = findPessoaFisicaById(pessoaFisica.getId());
	     pessoaFisicaRepository.delete(pessoaFisicaEncontrada); 
	}

	@Override
	@Transactional(readOnly=true)
	public List<PessoaFisica> findAll() {
		return pessoaFisicaRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public PessoaFisica findPessoaFisicaById(Long id) {
		return pessoaFisicaRepository.getOne(id);
	}

	@Override
	public Page<PessoaFisica> listaPessoaComPaginacao(PessoaFisicaFiltro pessoaFisicaFiltro, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
