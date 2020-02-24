package com.crm.domain.model.enumerate;

public enum TipoContato {
	
	RESIDENCIAL("Residencial"),
	COMERCIAL("Comercial");
	
	private String descricao;

	TipoContato(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}
