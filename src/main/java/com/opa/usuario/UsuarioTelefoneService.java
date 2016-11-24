package com.opa.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.opa.exceptions.IdentificadorNullException;
import com.opa.exceptions.TelefoneNaoEncontradoException;

@Service
public class UsuarioTelefoneService {

	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";
	private static final String MENSAGEM_TELEFONE_NAO_ENCONTRADO = "O telefone não foi encontrado!";

	@Autowired
	private UsuarioTelefoneRepository usuarioTelefoneRepositorio;

	public List<UsuarioTelefone> listar() {
		return usuarioTelefoneRepositorio.findAll();
	}

	public UsuarioTelefone buscar(String id) {
		UsuarioTelefone tel = null;

		try {
			tel = usuarioTelefoneRepositorio.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		return tel;
	}

	public UsuarioTelefone salvar(UsuarioTelefone telefone) {
		return usuarioTelefoneRepositorio.save(telefone);
	}

	public void deletar(String id) {
		try {
			usuarioTelefoneRepositorio.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new TelefoneNaoEncontradoException(MENSAGEM_TELEFONE_NAO_ENCONTRADO);
		}
	}

	public void atualizar(UsuarioTelefone telefone) {
		verificarExistencia(telefone);
		usuarioTelefoneRepositorio.save(telefone);
	}

	private void verificarExistencia(UsuarioTelefone tel) {
		buscar(tel.getId());
	}
}