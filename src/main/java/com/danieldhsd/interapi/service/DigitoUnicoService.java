package com.danieldhsd.interapi.service;

import org.springframework.stereotype.Service;

@Service
public class DigitoUnicoService {

	public String getEntradaFormatada(String n, int k) {
		String nTemp = "";
		for (int i = 0; i < k; i++) {
			nTemp += n;
		}
		
		return nTemp;
	}
	
	public Integer getDigitoUnico(String n, int k) {
		n = getEntradaFormatada(n, k);
		
		while(n.length() > 1) {
			n = getSomaDigitos(n);
		}

		return n != null && !n.isEmpty() ? Integer.valueOf(n) : 0;
	}
	
	private String getSomaDigitos(String numero) {
		Integer somaDosCaracteres = 0;
		
		for(int i = 0; i < numero.length(); i++) {
			char charAt = numero.charAt(i);
			
			if(Character.isDigit(charAt))
				somaDosCaracteres += Character.getNumericValue(charAt);
		}
		
		return String.valueOf(somaDosCaracteres);
	}
	
}
