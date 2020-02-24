package com.crm.service.exceptions;

public class RoleNaoEncontradoException extends EntidadeNaoEncontradaException {

	public RoleNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RoleNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de role com código %d", id));
	}

}
