package com.crm.domain.repository.query;

import java.util.Optional;

import com.crm.domain.model.Usuario;

public interface UsuarioRepositoryQuery {

	Usuario buscarUsuarioAtivoPeloEmail(String email);
	
	Optional<Usuario> findUsuarioActiveByEmail(String email);
	
}
