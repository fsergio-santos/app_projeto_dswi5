package com.crm.model.dto;

import javax.validation.constraints.NotNull;

public class PessoaDTO {

	private Long   id;
	private String nome;
	private String email;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull(message="o campo nome deve ser informado")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotNull(message="o campo email deve ser informado")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "PessoaDTO [nome=" + nome + ", email=" + email + "]";
	}
	
	
}
