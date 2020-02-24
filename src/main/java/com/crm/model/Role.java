package com.crm.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.crm.model.valid.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="TAB_ROLE")
@SequenceGenerator(name="ROLE_SEQUENCE",sequenceName="TAB_ROLE_SEQUENCE",
				initialValue=1, allocationSize=1)
public class Role implements Serializable{

	private static final long serialVersionUID = -5258227805102437262L;

	private Long id;
	private String nome;
	private List<Usuario> usuarios;
	
		
	public Role() {
		super();
	}

	public Role(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	@NotNull(groups = Groups.UsuarioId.class)
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ROLE_SEQUENCE")
	@Column(name="role_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonIgnore
	@ManyToMany(mappedBy="roles")
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", nome=" + nome + "]";
	}
	
	
	
}
