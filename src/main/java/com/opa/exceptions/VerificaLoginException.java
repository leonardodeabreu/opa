package com.opa.exceptions;

public class VerificaLoginException extends Exception {

	private static final long serialVersionUID = 1L;

	public VerificaLoginException(String mensagem) {
		super(mensagem);
	}

	public VerificaLoginException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}