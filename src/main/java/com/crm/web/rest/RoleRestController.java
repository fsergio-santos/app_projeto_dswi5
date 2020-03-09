package com.crm.web.rest;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crm.domain.model.Role;
import com.crm.domain.service.RoleService;
import com.crm.domain.service.exceptions.NegocioException;
import com.crm.domain.service.exceptions.RoleNaoEncontradoException;

@RestController
@RequestMapping(value="/roles")
public class RoleRestController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping(value = "/listar")
	public List<Role> listarRoles(){
		return roleService.findAll();
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Role> buscar(@PathVariable Long id) {
		Role role = roleService.findRoleById(id);
		return ResponseEntity.ok(role);
	}
	
	
	@PostMapping(value="/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public Role saveNewRole(@RequestBody Role role) {
		return roleService.save(role);
	}
	
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Role> atualizar(@PathVariable Long id,  @RequestBody Role role) {
		try {
		    Role roleCadastrada = roleService.findRoleById(id);
			BeanUtils.copyProperties(role, roleCadastrada, "id");
			roleCadastrada = roleService.save(roleCadastrada);
			return ResponseEntity.ok(roleCadastrada);
		} catch(RoleNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
	   Role roleCadastrada = roleService.findRoleById(id);
	   roleService.remove(roleCadastrada);	
	   return ResponseEntity.noContent().build();
	}
	
	
}
