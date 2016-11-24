package com.opa.exceptions;

public class TelefoneNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TelefoneNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public TelefoneNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
