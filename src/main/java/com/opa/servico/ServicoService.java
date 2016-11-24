package com.opa.servico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.opa.exceptions.IdentificadorNullException;
import com.opa.exceptions.UsuarioNaoEncontradoException;

@Service
public class ServicoService {

	private static final String MENSAGEM_SERVICO_NAO_ENCONTRADO = "O serviço não foi encontrado!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";

	@Autowired
	private ServicoRepository servicoRepositorio;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<Servico> recuperarServicosPaginando() {
		Pageable pageable = new PageRequest(0, 12);
		return servicoRepositorio.getAllServicos(pageable);
	}

	public List<Servico> listar() {
		return servicoRepositorio.findAll();
	}

	public Servico buscar(String id) {
		Servico servico = null;

		try {
			servico = servicoRepositorio.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		if (Objects.isNull(servico)) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_SERVICO_NAO_ENCONTRADO);
		}

		return servico;
	}

	public Servico salvar(Servico servico) {

		servico.setDataCadastro(new Date());
		return servicoRepositorio.save(servico);
	}

	public void deletar(String id) {
		try {
			servicoRepositorio.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_SERVICO_NAO_ENCONTRADO);
		}
	}

	public void atualizar(Servico servico) {
		verificarExistencia(servico);
		servicoRepositorio.save(servico);
	}

	private void verificarExistencia(Servico servico) {
		buscar(servico.getId());
	}

	public List<Servico> buscarServicoPorDono(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"s.id, " + 
								 	"s.titulo, " + 
									"s.descricao, " + 
								 	"s.imagem, " + 
									"s.data_cadastro," + 
								 	"s.fk_id_categoria, " + 
									"s.estado, " + 
								 	"s.cidade, " + 
								 	"s.usuario_id " + 
									"FROM public.servico s " + 
								 	"WHERE s.usuario_id = :usuario_id",
				parametros, new PreparedStatementCallback<List<Servico>>() {

					@Override
					public List<Servico> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<Servico> servico = new ArrayList<Servico>();

						while (resultadoDaConsulta.next()) {
							servico.add(new Servico(resultadoDaConsulta.getString("id"),
													resultadoDaConsulta.getString("titulo"), 
													resultadoDaConsulta.getString("descricao"),
													resultadoDaConsulta.getString("imagem"),
													resultadoDaConsulta.getDate("data_cadastro"),
													resultadoDaConsulta.getString("fk_id_categoria"),
													resultadoDaConsulta.getString("estado"), 
													resultadoDaConsulta.getString("cidade"),
//													EstadoServico.valueOf(resultadoDaConsulta.getString("status")),
													resultadoDaConsulta.getString("usuario_id")));
						}

						return servico;
					}
				});
	}
	
	public List<Servico> buscarServicoPorCategoria(String idCategoria) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("categoria_id", idCategoria);

		return jdbcTemplate.execute("SELECT " + 
									"s.id, " + 
								 	"s.titulo, " + 
									"s.descricao, " + 
								 	"s.imagem, " + 
									"s.data_cadastro," + 
								 	"s.fk_id_categoria, " + 
									"s.estado, " + 
								 	"s.cidade, " + 
								 	"s.usuario_id " + 
									"FROM public.servico s " + 
								 	"WHERE s.fk_id_categoria = :categoria_id",
				parametros, new PreparedStatementCallback<List<Servico>>() {

					@Override
					public List<Servico> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<Servico> servico = new ArrayList<Servico>();

						while (resultadoDaConsulta.next()) {
							servico.add(new Servico(resultadoDaConsulta.getString("id"),
													resultadoDaConsulta.getString("titulo"), 
													resultadoDaConsulta.getString("descricao"),
													resultadoDaConsulta.getString("imagem"),
													resultadoDaConsulta.getDate("data_cadastro"),
													resultadoDaConsulta.getString("fk_id_categoria"),
													resultadoDaConsulta.getString("estado"), 
													resultadoDaConsulta.getString("cidade"),
//													EstadoServico.valueOf(resultadoDaConsulta.getString("status")),
													resultadoDaConsulta.getString("usuario_id")));
						}

						return servico;
					}
				});
	}

}