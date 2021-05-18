package com.danieldhsd.interapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DigitoUnicoServiceTeste {

	@Autowired
	DigitoUnicoService digitoUnicoService;
	
	@Test
	public void calculaDigitoUnico() {
		Integer digitoUnico = digitoUnicoService.getDigitoUnico("5646", 2);
		assertEquals(digitoUnico, 6);
		
		digitoUnico = digitoUnicoService.getDigitoUnico("1234", 1);
		assertEquals(digitoUnico, 1);
		
		digitoUnico = digitoUnicoService.getDigitoUnico("1s23e40", 1);
		assertEquals(digitoUnico, 1);
		
		digitoUnico = digitoUnicoService.getDigitoUnico("1s23e40", 0);
		assertEquals(digitoUnico, 0);
		
		digitoUnico = digitoUnicoService.getDigitoUnico("dasdasda", 1);
		assertEquals(digitoUnico, 0);
		
		digitoUnico = digitoUnicoService.getDigitoUnico("123456789", 1);
		assertEquals(digitoUnico, 9);
		
		digitoUnico = digitoUnicoService.getDigitoUnico("123456789", 2);
		assertEquals(digitoUnico, 9);
	}
}
