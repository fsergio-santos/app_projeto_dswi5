package com.crm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.Pessoa;
import com.crm.repository.PessoaRepository;
import com.crm.repository.filtros.PessoaFiltro;
import com.crm.service.PessoaService;
import com.crm.service.exceptions.EmailExistente;

@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public Pessoa save(Pessoa pessoa) {
		Optional<Pessoa> optionalPessoa = findPessoaByEmail(pessoa.getEmail());
		if (optionalPessoa.isPresent()) {
			throw new EmailExistente("E-mail já cadastrado");
		}
		
		return pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa update(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	@Override
	public void remove(Pessoa pessoa) {
		pessoaRepository.deleteById(pessoa.getId());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Pessoa findPessoaById(Long id) {
		return pessoaRepository.getOne(id);
	}

	@Override
	public Page<Pessoa> listaPessoaComPaginacao(PessoaFiltro pessoaFiltro, Pageable pageable) {
		return pessoaRepository.listaPessoaComPaginacao(pessoaFiltro, pageable);
	}
	
	@Override
	public Optional<Pessoa> findPessoaByEmail(String email){
		return pessoaRepository.findPessoaByEmail(email);
	}

	@Override
	public List<Pessoa> findPessoaByName(String nome) {
		return pessoaRepository.findPessoaByName(nome);
	}

	
	
	
	
	
	
	
	
	
}
