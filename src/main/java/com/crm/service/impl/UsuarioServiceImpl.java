package com.crm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.Role;
import com.crm.model.Usuario;
import com.crm.repository.RoleRepository;
import com.crm.repository.UsuarioRepository;
import com.crm.service.UsuarioService;

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
		for (int i = 0; i < usuario.getRoles().size(); i++) {
            Role roleCadastrado = roleRepository.getOne(usuario.getRoles().get(i).getId());
         	autoridade.add(new SimpleGrantedAuthority("ROLE_"+roleCadastrado.getNome().toUpperCase()));
         	usuario.getRoles().get(i).setNome(roleCadastrado.getNome());
		}
		usuario.setPassword(encodeUsuarioPassword(usuario.getPassword()));
		return usuario = usuarioRepository.save(usuario);
	}

	@Override
	public Usuario update(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findUsuarioByName(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findUsuarioById(Long id) {
		return usuarioRepository.getOne(id);
	}

	@Override
	public Optional<Usuario> findUsuarioByEmail(String email) {
		return usuarioRepository.findUsuarioActiveByEmail(email);
	}
	
	private String encodeUsuarioPassword(String password) {
		 return passwordEncoder.encode(password);
	}

	
}
