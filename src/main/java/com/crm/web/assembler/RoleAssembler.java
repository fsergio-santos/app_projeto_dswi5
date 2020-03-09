package com.crm.web.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.domain.model.Role;
import com.crm.web.dto.RoleDTO;

@Component
public class RoleAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public RoleDTO toRoleDTO(Role role) {
		return modelMapper.map(role, RoleDTO.class);
	}
	
	public Role toRole(RoleDTO roleDTO) {
		return modelMapper.map(roleDTO, Role.class);
	}
	
}
