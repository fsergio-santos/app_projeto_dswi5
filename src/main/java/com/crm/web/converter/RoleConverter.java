package com.crm.web.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.crm.domain.model.Role;
import com.crm.domain.service.RoleService;


@Component
public class RoleConverter implements Converter<String, Role>{
	
	@Autowired
	private RoleService roleService;

	@Override
	public Role convert(String string) {
        if (string.isEmpty()) {
        	return null;
        }
        Long id = Long.valueOf(string);
		return roleService.findRoleById(id);
	}

}
