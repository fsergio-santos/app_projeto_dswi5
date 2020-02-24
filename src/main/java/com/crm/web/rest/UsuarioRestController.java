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
import org.springframework.web.bind.annotation.RestController;

import com.crm.domain.model.Usuario;
import com.crm.domain.service.UsuarioService;
import com.crm.domain.service.exceptions.NegocioException;
import com.crm.domain.service.exceptions.UsuarioNaoEncontradoException;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioRestController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(value = "/listar")
	public List<Usuario> listarUsuario(){
		return usuarioService.findAll();
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Usuario> buscar(@PathVariable Long id){
		Usuario usuario = usuarioService.findUsuarioById(id);
		return ResponseEntity.ok(usuario);
	}
	
	
	@PostMapping(value="/salvar")
	public ResponseEntity<Usuario> saveNewUsuario(@RequestBody Usuario usuario) {
		Usuario user = usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id,  @RequestBody Usuario usuario) {
		try {
		    Usuario usuarioCadastrado = usuarioService.findUsuarioById(id);
			BeanUtils.copyProperties(usuario, usuarioCadastrado, "id");
			usuarioCadastrado = usuarioService.save(usuarioCadastrado);
			return ResponseEntity.ok(usuarioCadastrado);
		} catch(UsuarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
	   Usuario usuarioCadastrado = usuarioService.findUsuarioById(id);
	   usuarioService.remove(usuarioCadastrado);	
	   return ResponseEntity.noContent().build();
	}
	
	
	
	
}
