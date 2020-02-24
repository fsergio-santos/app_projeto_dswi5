package com.crm.service;

import java.util.List;

import com.crm.model.Telefone;

public interface TelefoneService {
	
	Telefone save(Telefone telefone);
	Telefone update(Telefone telefone);
	void remove(Telefone telefone);
	List<Telefone> findAll();
	Telefone findTelefoneById(Long id);

}
