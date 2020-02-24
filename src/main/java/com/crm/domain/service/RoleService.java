package com.crm.domain.service;

import java.util.List;

import com.crm.domain.model.Role;

public interface RoleService {
	
	Role save(Role role);
	Role update(Role role);
	void remove(Role role);
	List<Role> findAll();
	Role findRoleById(Long id);

}
