package com.crm.web.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.crm.model.Role;
import com.crm.service.RoleService;
import com.crm.service.exceptions.EntidadeEmUsoException;
import com.crm.service.exceptions.EntidadeNaoEncontradaException;
import com.crm.service.exceptions.NegocioException;
import com.crm.web.exception.Problem;
import com.crm.web.exception.ProblemType;

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
		if (role != null) {
			return ResponseEntity.ok(role);
		}
		return ResponseEntity.notFound().build();
	}
	
	/*
	 * @GetMapping("/buscar/{id}") public Role buscar(@PathVariable Long id) {
	 * return roleService.findRoleById(id); }
	 */
	
	@PostMapping(value="/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public Role saveNewRole(@RequestBody Role role) {
		return roleService.save(role);
	}
	
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Role> atualizar(@PathVariable Long id,  @RequestBody Role role) {
		Role roleCadastrada = roleService.findRoleById(id);
		if ( roleCadastrada != null) {
			BeanUtils.copyProperties(role, roleCadastrada, "id");
			roleCadastrada = roleService.save(roleCadastrada);
			return ResponseEntity.ok(roleCadastrada);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
	   Role roleCadastrada = roleService.findRoleById(id);
	   roleService.remove(roleCadastrada);	
	   return ResponseEntity.noContent().build();
	}
	
	/*
	 
	   Começar aula de exception por aqui, 
	   para usuario role.
	   depois ir para exception global. 
	 
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		Problem problem = Problem.builder()
				  .addTimestamp(LocalDateTime.now())
				  .addUserMessage(ex.getMessage()).build();
		
		return ResponseEntity.status( HttpStatus.NOT_FOUND).body(problem);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
	
		Problem problem = Problem.builder()
				  .addTimestamp(LocalDateTime.now())
				  .addUserMessage(ex.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
		
		Problem problem = Problem.builder()
				  .addTimestamp(LocalDateTime.now())
				  .addUserMessage(ex.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
	}
	
	*/
	
	
}
