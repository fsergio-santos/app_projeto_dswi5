package com.crm.web.exception;

import java.util.HashMap;
import java.util.Map;

import com.crm.model.dto.PessoaDTO;

public class ErroCadastroPessoa {
	
	//private PessoaDTO pessoaDTO;
	private boolean   validated;
	private Map<String, String> erroMensagem = new HashMap<String, String>();
	private String mensagem;
	private Long idClasse; 
	
	/*public PessoaDTO getPessoaDTO() {
		return pessoaDTO;
	}
	public void setPessoaDTO(PessoaDTO pessoaDTO) {
		this.pessoaDTO = pessoaDTO;
	}*/
	
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	
	public Map<String, String> getErroMensagem() {
		return erroMensagem;
	}
	public void setErroMensagem(Map<String, String> erroMensagem) {
		this.erroMensagem = erroMensagem;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Long getIdClasse() {
		return idClasse;
	}
	public void setIdClasse(Long idClasse) {
		this.idClasse = idClasse;
	}
	
	
	
	
	

}
