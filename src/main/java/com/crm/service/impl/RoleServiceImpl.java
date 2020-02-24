package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.Role;
import com.crm.repository.RoleRepository;
import com.crm.service.RoleService;
import com.crm.service.exceptions.EntidadeEmUsoException;
import com.crm.service.exceptions.RoleNaoEncontradoException;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role update(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void remove(Role role) {
		try {
		roleRepository.deleteById(role.getId());
		} catch ( EmptyResultDataAccessException e) {
		   throw new RoleNaoEncontradoException(role.getId());
		} catch (DataIntegrityViolationException  e) {
	       throw new EntidadeEmUsoException(String.format("A Role de código %d não pode ser removida, pois está em uso", role.getId()));
		}
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findRoleById(Long id) {
		return roleRepository.findById(id).orElseThrow(()-> new RoleNaoEncontradoException(id));
	}

}
