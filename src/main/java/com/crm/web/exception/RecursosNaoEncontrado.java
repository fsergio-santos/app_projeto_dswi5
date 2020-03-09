package com.crm.web.exception;

import com.crm.domain.service.exceptions.NegocioException;

public class RecursosNaoEncontrado extends NegocioException {

	public RecursosNaoEncontrado(String mensagem) {
		super(mensagem);
	}

}
