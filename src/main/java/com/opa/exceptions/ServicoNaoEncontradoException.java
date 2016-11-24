package com.opa.exceptions;

public class ServicoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServicoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ServicoNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
