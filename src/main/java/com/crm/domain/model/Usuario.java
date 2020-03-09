package com.crm.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.crm.domain.model.valid.Groups;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name="TAB_USUARIO")
@SequenceGenerator(name="USUARIO_SEQUENCE",sequenceName="TAB_USUARIO_SEQUENCE",
					initialValue=1, allocationSize=1)
public class Usuario implements UserDetails, Serializable {

	private static final long serialVersionUID = 5737698097167590600L;
	
	private Long id;
	private String email;
	private String username;
	private String password;
	private String contraSenha;
	private Date lastLogin;
	private boolean ativo = Boolean.TRUE;
	
	private List<Role> roles = new ArrayList<>();
	
		
	public Usuario() {
		super();
	}
	
	
	
	public Usuario(Long id, String email, String username, String password, String contraSenha, Date lastLogin,
			boolean ativo, List<Role> roles) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.contraSenha = contraSenha;
		this.lastLogin = lastLogin;
		this.ativo = ativo;
		this.roles = roles;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="USUARIO_SEQUENCE")
	@Column(name="usuario_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Email
	@NotBlank
	@Column(name="USUARIO_EMAIL", nullable = false, length = 100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	@NotBlank
	@Column(name="USUARIO_PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	@Transient
	public String getContraSenha() {
		return contraSenha;
	}
	public void setContraSenha(String contraSenha) {
		this.contraSenha = contraSenha;
	}
	
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern="dd/MM/yyyy")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="usuario_last_login",nullable=true, columnDefinition="DATE")
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.UsuarioId.class)
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="TAB_USUARIO_ROLE", 
			   joinColumns=@JoinColumn(name="usuario_id"),
			   inverseJoinColumns = @JoinColumn(name="role_id")
			)
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", password=" + password + ", contraSenha=" + contraSenha
				+ ", lastLogin=" + lastLogin + ", ativo=" + ativo + "]";
	}

	@Override
	@Transient
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> autoridade = new ArrayList<GrantedAuthority>();
		for (Role role : this.getRoles()) {
			autoridade.add(new SimpleGrantedAuthority("ROLE_"+role.getNome().toUpperCase()));
		}
		return autoridade;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return ativo;
	}

	@JsonIgnore
	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return ativo;
	}

	@JsonIgnore
	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return ativo;
	}

	@JsonIgnore
	@Override
	@Transient
	public boolean isEnabled() {
		return ativo;
	}

}
