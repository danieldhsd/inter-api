package com.danieldhsd.interapi.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.danieldhsd.interapi.model.DigitoUnico;
import com.danieldhsd.interapi.service.DigitoUnicoService;

public class DigitoUnicoCacheTeste {

	private DigitoUnicoCache digitoUnicoCache;
	
	private DigitoUnicoService digitoUnicoService;
	
	@BeforeEach
	public void setUp() {
		this.digitoUnicoCache = new DigitoUnicoCache();
		this.digitoUnicoService = new DigitoUnicoService();
	}
	
	@Test
	public void testeMaxSizeCache() {
		addDigitoUnicoCache("123456", 1);
		addDigitoUnicoCache("123456", 2);
		addDigitoUnicoCache("123456", 3);
		addDigitoUnicoCache("123456", 4);
		addDigitoUnicoCache("123456", 5);
		addDigitoUnicoCache("123456", 6);
		addDigitoUnicoCache("123456", 7);
		addDigitoUnicoCache("123456", 8);
		addDigitoUnicoCache("123456", 9);
		addDigitoUnicoCache("123456", 10);
		addDigitoUnicoCache("123456", 11);
		addDigitoUnicoCache("123456", 12);
		
		assertEquals(10, digitoUnicoCache.cacheMap.size());
	}
	
	private void addDigitoUnicoCache(String entrada, Integer multiplicador) {
		DigitoUnico digitoUnico = new DigitoUnico();
		digitoUnico.setEntrada(entrada);
		digitoUnico.setMultiplicador(multiplicador);
		
		String entradaFormatada = digitoUnicoService.getEntradaFormatada(digitoUnico.getEntrada(), digitoUnico.getMultiplicador());

		digitoUnico.setResultado(digitoUnicoService.getDigitoUnico(digitoUnico.getEntrada(), digitoUnico.getMultiplicador()));
		
		digitoUnicoCache.put(entradaFormatada, digitoUnico);
	}
}
