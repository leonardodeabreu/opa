package com.opa.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.opa.exceptions.IdentificadorNullException;
import com.opa.exceptions.UsuarioNaoEncontradoException;

@ControllerAdvice
public class UsuarioExceptionHandler {

	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<Erro> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException e,
			HttpServletRequest request) {

		Erro erro = new Erro();
		erro.setStatus(HttpStatus.NOT_FOUND.value());
		erro.setTitulo("O usuário não pôde ser encontrado!");
		erro.setMensagemDesenvolvedor("http://erros.opa.com.br/404");

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

	@ExceptionHandler(IdentificadorNullException.class)
	public ResponseEntity<Erro> handleIdentificadorNullException(IdentificadorNullException e,
			HttpServletRequest request) {

		Erro erro = new Erro();
		erro.setStatus(HttpStatus.BAD_REQUEST.value());
		erro.setTitulo("O ID não pode ser nulo!");
		erro.setMensagemDesenvolvedor("http://erros.opa.com.br/400");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}