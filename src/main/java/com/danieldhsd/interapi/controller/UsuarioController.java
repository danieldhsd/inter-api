package com.danieldhsd.interapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danieldhsd.interapi.exception.UsuarioJaExistenteException;
import com.danieldhsd.interapi.exception.UsuarioNaoEncontradoException;
import com.danieldhsd.interapi.model.Usuario;
import com.danieldhsd.interapi.repository.UsuariosRepository;
import com.danieldhsd.interapi.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public List<Usuario> buscarTodos() {
		List<Usuario> usuarios = usuariosRepository.findAll();
		
		if(usuarios != null && !usuarios.isEmpty()) {
			for(Usuario usuario : usuarios)
				usuario = usuarioService.criptografarUsuario(usuario);
		}
		
		return usuarios;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
		Optional<Usuario> usuario = usuariosRepository.findById(id);

		if(usuario.isPresent())
			return ResponseEntity.ok(usuarioService.criptografarUsuario(usuario.get()));
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Usuario usuario) {
		try {
			usuario = usuarioService.salvar(usuario);
			
		} catch (UsuarioJaExistenteException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok(usuario);
	}
	
	@PutMapping("/{id}")
	public Usuario atualizar(@RequestBody Usuario usuario, @PathVariable Long id) {
		Optional<Usuario> usuarioExistente = usuariosRepository.findById(id);
		
		if(!usuarioExistente.isPresent())
			throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
		
		usuario.setId(id);
		
		return usuarioService.salvar(usuario);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> apagar(@PathVariable Long id) {
		try {
			Optional<Usuario> usuarioExistente = usuariosRepository.findById(id);
			
			if(!usuarioExistente.isPresent())
				return ResponseEntity.notFound().build();

			usuariosRepository.delete(usuarioExistente.get());
			
			return ResponseEntity.noContent().build();
			
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> setChavePublica(@RequestBody Usuario usuario, @PathVariable Long id) {
		Optional<Usuario> usuarioOptional = usuariosRepository.findById(id);
		
		if(!usuarioOptional.isPresent())
			return ResponseEntity.badRequest().body("Usuário não encontrado!");
		
		Usuario usuarioExistente = usuarioOptional.get();
		usuarioExistente.setChavePublicaCriptografia(usuario.getChavePublicaCriptografia());
		
		usuarioService.salvar(usuarioExistente);
		
		return ResponseEntity.ok(usuarioExistente);
	}
}
