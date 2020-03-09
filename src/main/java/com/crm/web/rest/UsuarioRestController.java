package com.crm.web.rest;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.crm.domain.service.exceptions.ValidacaoException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioRestController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private SmartValidator validator; //aula sobre validação.
	
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
	
	@PatchMapping("/{id}")
	public ResponseEntity<Usuario> atualizarParcial(@PathVariable Long id, 
													@RequestBody Map<String, Object> campos, 
													HttpServletRequest request) {
		Usuario UsuarioAtual = usuarioService.findUsuarioById(id);
		
		merge(campos, UsuarioAtual, request);
		validate(UsuarioAtual, "Usuario");
		
		return atualizar(id, UsuarioAtual);
	}

	private void validate(Usuario Usuario, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(Usuario, objectName);
		validator.validate(Usuario, bindingResult);
		
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	private void merge(Map<String, Object> dadosOrigem, Usuario UsuarioDestino,
			HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Usuario UsuarioOrigem = objectMapper.convertValue(dadosOrigem, Usuario.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Usuario.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, UsuarioOrigem);
				
				ReflectionUtils.setField(field, UsuarioDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	
	
	
}
