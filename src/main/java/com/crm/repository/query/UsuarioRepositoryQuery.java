package com.crm.repository.query;

import java.util.Optional;

import com.crm.model.Usuario;

public interface UsuarioRepositoryQuery {

	Usuario buscarUsuarioAtivoPeloEmail(String email);
	
	Optional<Usuario> findUsuarioActiveByEmail(String email);
	
}
