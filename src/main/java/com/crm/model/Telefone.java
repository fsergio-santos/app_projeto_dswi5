package com.crm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.crm.model.enumerate.TipoContato;
import com.crm.model.enumerate.TipoTelefone;

@Entity
@Table(name="TAB_TELEFONE")
@SequenceGenerator(name="TELEFONE_SEQUENCE", 
                   sequenceName="TAB_TELEFONE_SEQUENCE",
                   initialValue=1, allocationSize=1)
public class Telefone {

	private Long id;
	private TipoContato tipoContato;
	private TipoTelefone tipoTelefone;
	private String numeroTelefone;
	
	private Pessoa pessoa;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="TELEFONE_SEQUENCE")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoContato getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContato tipoContato) {
		this.tipoContato = tipoContato;
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	@ManyToOne(targetEntity=Pessoa.class, cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="PESSOA_ID",nullable=false,
	            referencedColumnName="PESSOA_ID",
	            foreignKey=@ForeignKey(name="FK_TELEFONE_PESSOA"))
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
	
	
	
	
	
	
	
}
