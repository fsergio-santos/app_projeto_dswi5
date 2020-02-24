package com.crm.domain.service.exceptions;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public UsuarioNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de usuário com código %d", id));
	}


}
