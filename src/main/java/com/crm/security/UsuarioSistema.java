package com.crm.security;

import org.springframework.security.core.userdetails.User;

import com.crm.domain.model.Usuario;

public class UsuarioSistema extends User{

	private Usuario usuario;
	
	public UsuarioSistema(Usuario usuario) {
		super(usuario.getUsername(),
			  usuario.getPassword(), 
			  usuario.isEnabled(), 
			  usuario.isAccountNonExpired(), 
			  usuario.isCredentialsNonExpired(), 
			  usuario.isAccountNonLocked(), 
			  usuario.getAuthorities());
		
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	
	
}
