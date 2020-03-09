package com.crm.domain.service.exceptions;

import org.springframework.validation.BindingResult;

public class ValidacaoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;

	public ValidacaoException(BindingResult mensagem) {
		super(mensagem.toString());
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

    

}
