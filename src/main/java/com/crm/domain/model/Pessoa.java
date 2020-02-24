package com.crm.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="TAB_PESSOA")
@SequenceGenerator(name="PESSOA_SEQUENCE", 
                   sequenceName="TAB_PESSOA_SEQUENCE",
                   initialValue=1, allocationSize=1)
/*@SQLDelete(sql="UPDATE TAB_PESSOA SET registro_deletado = true WHERE pessoa_id = ?")
@Where(clause="registro_deletado = false")*/
public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1617861871023287727L;

	private Long id;
	private String nome;
	private String email;
	
	
	private PessoaFisica pessoaFisica;
	
	private List<Telefone> telefones;
	
	private boolean registro_deletado = false;
	
	public Pessoa() {
	}


	public Pessoa(Long id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	
	
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="PESSOA_SEQUENCE") //usar em qualquer banco de dados
    //@GeneratedValue(strategy=GenerationType.IDENTITY)  - USAR SOMENTE NO MYSQL
    @Column(name="PESSOA_ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Size(min=5, max=100, message="No mínimo 5 caracteres e no máximo 100 caracteres")
	@NotBlank(message="O nome da pessoa deve ser digitado!")
	@NotNull(message="O nome da pessoa deve ser digitado!")
	@Column(name="PESSOA_NOME",length=100, nullable=false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
    @Email(message="E-mail inválido!")
	@Size(min=5, max=100, message="No mínimo 5 caracteres e no máximo 100 caracteres")
	@NotBlank(message="O e-mail da pessoa deve ser digitado!")
	@NotNull(message="O e-mail da pessoa deve ser digitado!")
	@Column(name="PESSOA_EMAIL",length=100, nullable=false,unique=true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@OneToOne(mappedBy="pessoa",fetch=FetchType.LAZY)
	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}


	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

    @OneToMany(mappedBy="pessoa", fetch=FetchType.LAZY) 	
	public List<Telefone> getTelefones() {
		return telefones;
	}


	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}


	@Column(name="REGISTRO_DELETADO")
	public boolean isRegistro_deletado() {
		return registro_deletado;
	}

	public void setRegistro_deletado(boolean registro_deletado) {
		this.registro_deletado = registro_deletado;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", email=" + email + "]";
	}
	
	
	
	

}
