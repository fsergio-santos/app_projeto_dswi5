package com.crm.model.enumerate;

public enum TipoTelefone {
	
	FIXO("Fixo"),
	MOVEL("Celular");
	
	private String descricao;

	TipoTelefone(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	

}
