package com.crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crm.model.Usuario;


public interface UsuarioService {

	Usuario save(Usuario usuario);
	Usuario update(Usuario usuario);
	void remove(Usuario usuario);
	List<Usuario> findAll();
	
	List<Usuario> findUsuarioByName(String nome);
	Usuario findUsuarioById(Long id);
	//Page<Usuario> listaUsuarioComPaginacao(UsuarioFiltro usuarioFiltro, Pageable pageable);
	Optional<Usuario> findUsuarioByEmail(String email);
	
}
