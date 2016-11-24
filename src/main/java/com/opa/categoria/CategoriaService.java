package com.opa.categoria;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.opa.exceptions.UsuarioNaoEncontradoException;
import com.opa.exceptions.IdentificadorNullException;

@Service
public class CategoriaService {

	private static final String MENSAGEM_CATEGORIA_NAO_ENCONTRADO = "A categoria não foi encontrada!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";

	@Autowired
	private CategoriaRepository categoriaRepositorio;

	public List<Categoria> listar() {
		return categoriaRepositorio.findAll();
	}

	public Categoria buscar(String id) {
		Categoria categoria = null;

		try {
			categoria = categoriaRepositorio.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		if (Objects.isNull(categoria)) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_CATEGORIA_NAO_ENCONTRADO);
		}

		return categoria;
	}

	public Categoria salvar(Categoria categoria) {
		return categoriaRepositorio.save(categoria);
	}

	public void deletar(String id) {
		try {
			categoriaRepositorio.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_CATEGORIA_NAO_ENCONTRADO);
		}
	}

	public void atualizar(Categoria categoria) {
		verificarExistencia(categoria);
		categoriaRepositorio.save(categoria);
	}

	private void verificarExistencia(Categoria categoria) {
		buscar(categoria.getId());
	}

}