package com.danieldhsd.interapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danieldhsd.interapi.cache.DigitoUnicoCache;
import com.danieldhsd.interapi.model.DigitoUnico;
import com.danieldhsd.interapi.model.Usuario;
import com.danieldhsd.interapi.repository.DigitosUnicosRepository;
import com.danieldhsd.interapi.repository.UsuariosRepository;
import com.danieldhsd.interapi.service.DigitoUnicoService;

@RestController
@RequestMapping("/digito")
public class DigitoController {

	@Autowired
	private DigitosUnicosRepository digitosUnicosRepository;
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private DigitoUnicoService digitoUnicoService;
	
	private DigitoUnicoCache digitoUnicoCache = new DigitoUnicoCache();
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<DigitoUnico>> buscarTodosPorUsuario(@PathVariable Long id) {
		List<DigitoUnico> digitosUnicos = digitosUnicosRepository.findAllDigitosUnicosByUsuario(id);
		
		if(digitosUnicos == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(digitosUnicos);
	}
	
	@PostMapping
	public ResponseEntity<?> calculaDigitoUnico(@RequestBody DigitoUnico digitoUnico) {
		String entradaFormatada = digitoUnicoService.getEntradaFormatada(digitoUnico.getEntrada(), digitoUnico.getMultiplicador());
		
		if(digitoUnicoCache.containsKey(entradaFormatada))
			return ResponseEntity.ok(digitoUnicoCache.get(entradaFormatada));
		
		digitoUnico.setResultado(digitoUnicoService.getDigitoUnico(digitoUnico.getEntrada(), digitoUnico.getMultiplicador()));

		if(digitoUnico.getUsuario() != null) {
			Optional<Usuario> usuarioExistente = usuariosRepository.findById(digitoUnico.getUsuario().getId());
			digitoUnico.setUsuario(usuarioExistente.isPresent() ? usuarioExistente.get() : null);
			digitoUnico = digitosUnicosRepository.save(digitoUnico);
		}
		
		digitoUnicoCache.put(entradaFormatada, digitoUnico);
		
		return ResponseEntity.ok(digitoUnico);
	}
}
