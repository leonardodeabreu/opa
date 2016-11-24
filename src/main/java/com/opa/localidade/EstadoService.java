package com.opa.localidade;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.opa.exceptions.IdentificadorNullException;
import com.opa.exceptions.UsuarioNaoEncontradoException;

@Service
public class EstadoService {

	private static final String MENSAGEM_ESTADO_NAO_ENCONTRADO = "O estado não foi encontrado!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";

	@Autowired
	private EstadoRepository repo;

	public List<Estado> listar() {
		return repo.findAll();
	}

	public Estado buscar(String id) {
		Estado estado = null;

		try {
			estado = repo.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		if (Objects.isNull(estado)) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_ESTADO_NAO_ENCONTRADO);
		}

		return estado;
	}

}
