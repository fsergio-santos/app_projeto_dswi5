package com.crm.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crm.domain.model.Pessoa;
import com.crm.domain.service.PessoaService;
import com.crm.web.dto.PessoaDTO;
import com.crm.web.exception.ErroCadastroPessoa;

@RestController
@RequestMapping(value="/rest",
       consumes=MediaType.APPLICATION_JSON_VALUE,
       produces=MediaType.APPLICATION_JSON_VALUE
	)
public class PessoaRestController {

	@Autowired
	private PessoaService pessoaService;
	
    @ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/pessoa/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pessoa> buscarPessoaById(@RequestParam("id") Long id){
		Pessoa pessoa = pessoaService.findPessoaById(id);
		return pessoa == null 
			   ? ResponseEntity.notFound().build() 
		       : ResponseEntity.ok(pessoa);
	}
    
    
    
    @ResponseBody
    @RequestMapping(value="/pessoa", 
                    method=RequestMethod.GET,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pessoa> buscarPessoa(String nome){
    	validarNomePesquisa(nome);
    	List<Pessoa> listaPessoa = pessoaService.findPessoaByName(nome);
    	return listaPessoa;
    }
    
	
    private void validarNomePesquisa(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}

    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> pegarErroIllegalArgumentException(IllegalArgumentException e){
           return ResponseEntity.badRequest().build(); 	
    }
    


	@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/pessoa/salvar_pessoa", method=RequestMethod.POST)
    public ResponseEntity<ErroCadastroPessoa> salvarPessoaModal(@RequestBody PessoaDTO pessoaDTO, BindingResult result){
        
    	boolean erro = false;
    	
    	ErroCadastroPessoa erroCadastroPessoa = new ErroCadastroPessoa();
    	
    	if ( StringUtils.isEmpty(pessoaDTO.getNome())) {
    		 erroCadastroPessoa.setValidated(false);
    		 erroCadastroPessoa.getErroMensagem().put("nome","o nome deve ser informado!" );
    		 erroCadastroPessoa.setMensagem(null);
    		 erro = true;
    	} 
    	if (StringUtils.isEmpty(pessoaDTO.getEmail())){
    		 erroCadastroPessoa.setValidated(false);
    		 erroCadastroPessoa.getErroMensagem().put("email","o email deve ser informado!" );
    		 erroCadastroPessoa.setMensagem(null);
    		 erro = true;
    	} 
    	if ( erro == false ) {
    		Pessoa pessoa = new Pessoa();
    		pessoa.setNome(pessoaDTO.getNome());
    		pessoa.setEmail(pessoaDTO.getEmail());
    		pessoa = pessoaService.save(pessoa);
    		erroCadastroPessoa.setIdClasse(pessoa.getId());
    		erroCadastroPessoa.setValidated(true);
    		erroCadastroPessoa.setErroMensagem(null);
    		erroCadastroPessoa.setMensagem("Pessoa cadastrada com sucesso!");
    	}
    	return ResponseEntity.ok(erroCadastroPessoa);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
}
