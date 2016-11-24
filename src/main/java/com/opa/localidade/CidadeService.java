package com.opa.localidade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.opa.exceptions.IdentificadorNullException;
import com.opa.exceptions.UsuarioNaoEncontradoException;

@Service
public class CidadeService {

	private static final String MENSAGEM_CIDADE_NAO_ENCONTRADA = "A cidade não foi encontrada!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";

	@Autowired
	private CidadeRepository repo;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<Cidade> listar() {
		return repo.findAll();
	}

	public List<Cidade> recuperarCidadePorEstado(String idEstado) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("estado_id", idEstado);

		return jdbcTemplate.execute("select " + 
									"nome " + 
									"from public.cidade where estado_id = :estado_id", parametros, new PreparedStatementCallback<List<Cidade>>() {

					@Override
					public List<Cidade> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<Cidade> cidade = new ArrayList<Cidade>();

						while (resultadoDaConsulta.next()) {
							cidade.add(new Cidade(resultadoDaConsulta.getString("nome")));

						}

						return cidade;
					}
				});

	}

	public Cidade buscar(String id) {
		Cidade cidade = null;

		try {
			cidade = repo.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		if (Objects.isNull(cidade)) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_CIDADE_NAO_ENCONTRADA);
		}

		return cidade;
	}

}
