package com.crm.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crm.model.Role;
import com.crm.model.Usuario;
import com.crm.service.RoleService;
import com.crm.service.UsuarioService;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioRestController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(value = "/listar")
	public List<Usuario> listarUsuario(){
		return usuarioService.findAll();
	}
	
	@PostMapping(value="/salvar")
	public ResponseEntity<Usuario> saveNewUsuario(@RequestBody Usuario usuario) {
		Usuario user = usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
}
