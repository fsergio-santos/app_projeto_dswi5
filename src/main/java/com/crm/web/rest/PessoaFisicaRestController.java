package com.crm.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.model.Pessoa;
import com.crm.model.PessoaFisica;
import com.crm.service.PessoaFisicaService;

@RestController
@RequestMapping(value="/rest",
       consumes=MediaType.APPLICATION_JSON_VALUE,
       produces=MediaType.APPLICATION_JSON_VALUE
	)
public class PessoaFisicaRestController {

	@Autowired
	private PessoaFisicaService pessoaFisicaService;
	
	
	@RequestMapping(value="/pessoa_fisica/salvar_pessoa_fisica")
    public ResponseEntity<PessoaFisica> salvarPessoaModal(@RequestBody PessoaFisica pessoaFisica){
    	
    	pessoaFisica = pessoaFisicaService.save(pessoaFisica);
    	
    	
    	return null;
    }
	
}
