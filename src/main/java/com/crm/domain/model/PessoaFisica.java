package com.crm.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.crm.domain.model.enumerate.Sexo;

@Entity
@Table(name="TAB_PESSOA_FISICA")
@SequenceGenerator(name="pessoa_fisica_sequence", 
                   sequenceName="tab_pessoa_fisica_sequence",
                   initialValue=1, allocationSize=1)
public class PessoaFisica {

	private Long   id;
	private Date   dataNascimento;
	private String nomePai;
	private String nomeMae;
	private Sexo   sexo;
	private String cpf;
	
	private Pessoa pessoa;
	
	
	public PessoaFisica() {
	}
	
	public PessoaFisica(Long id, Date dataNascimento, String nomePai, String nomeMae, Sexo sexo, String cpf) {
		this.id = id;
		this.dataNascimento = dataNascimento;
		this.nomePai = nomePai;
		this.nomeMae = nomeMae;
		this.sexo = sexo;
		this.cpf = cpf;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pessoa_fisica_sequence")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Temporal(TemporalType.DATE)
    public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

    //@MapsId
    @OneToOne(targetEntity=Pessoa.class, fetch=FetchType.LAZY)
	@JoinColumn(name="PESSOA_ID",referencedColumnName="PESSOA_ID")
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
	
	
	
}
