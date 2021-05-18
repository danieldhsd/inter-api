package com.danieldhsd.interapi.exception;

public class UsuarioJaExistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UsuarioJaExistenteException(String message) {
		super(message);
	}
}
