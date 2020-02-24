package com.crm.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.domain.model.Role;
import com.crm.domain.model.Usuario;
import com.crm.domain.repository.RoleRepository;
import com.crm.domain.repository.UsuarioRepository;
import com.crm.domain.service.UsuarioService;
import com.crm.domain.service.exceptions.NegocioException;
import com.crm.domain.service.exceptions.RoleNaoEncontradoException;
import com.crm.domain.service.exceptions.UsuarioNaoEncontradoException;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Usuario save(Usuario usuario) {
		List<GrantedAuthority> autoridade = new ArrayList<GrantedAuthority>();
		if (usuario.getRoles().isEmpty()) {
			throw new NegocioException("Usuário tem que pertencer a um grupo do sistema");
		}
		for (int i = 0; i < usuario.getRoles().size(); i++) {
			try {
	            Role roleCadastrado = roleRepository.getOne(usuario.getRoles().get(i).getId());
	         	autoridade.add(new SimpleGrantedAuthority("ROLE_"+roleCadastrado.getNome().toUpperCase()));
	         	usuario.getRoles().get(i).setNome(roleCadastrado.getNome());
			} catch(EntityNotFoundException e) {
				throw new RoleNaoEncontradoException(usuario.getRoles().get(i).getId()); 	
			}
		}
		usuario.setPassword(encodeUsuarioPassword(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario update(Usuario usuario) {
		return this.save(usuario);
	}

	@Override
	public void remove(Usuario usuario) {
        try {
        	usuarioRepository.deleteById(usuario.getId());
        } catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuario.getId());
		}
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public List<Usuario> findUsuarioByName(String nome) {
		return null;
	}

	@Override
	public Usuario findUsuarioById(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id)); 
	}

	@Override
	public Optional<Usuario> findUsuarioByEmail(String email) {
		return usuarioRepository.findUsuarioActiveByEmail(email);
	}
	
	private String encodeUsuarioPassword(String password) {
		 return passwordEncoder.encode(password);
	}

	
}
