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
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.opa.exceptions.IdentificadorNullException;
import com.opa.exceptions.UsuarioNaoEncontradoException;

@Service
public class HistoricoServicoService {
	
	private static final String MENSAGEM_HISTORICO_SERVICO_NAO_ENCONTRADO = "O histórico do serviço não foi encontrado!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";

	@Autowired
	private HistoricoServicoRepository historicoServicoRepositorio;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<HistoricoServico> listar() {
		return historicoServicoRepositorio.findAll();
	}

	public HistoricoServico buscar(String id) {
		HistoricoServico hist = null;

		try {
			hist = historicoServicoRepositorio.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		if (Objects.isNull(hist)) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_HISTORICO_SERVICO_NAO_ENCONTRADO);
		}

		return hist;
	}

	public HistoricoServico salvar(HistoricoServico hist) {

		hist.setStatus(EstadoServico.CONTATO_SOLICITADO);
		hist.setInicio(new Date());
		hist.setUltimaAlteracao(new Date());
		return historicoServicoRepositorio.save(hist);
	}

	public HistoricoServico atualizar(HistoricoServico hist) {
		verificarExistencia(hist);
		
		if (hist.getStatus().equals(EstadoServico.CONTATO_SOLICITADO)) {
			hist.setStatus(EstadoServico.ABERTO);
		} else if (hist.getStatus().equals(EstadoServico.ABERTO)) {
			hist.setStatus(EstadoServico.FECHAMENTO_SOLICITADO);
		} else if (hist.getStatus().equals(EstadoServico.FECHAMENTO_SOLICITADO)) {
			hist.setStatus(EstadoServico.FECHADO);
		}
		
		hist.setUltimaAlteracao(new Date());
		historicoServicoRepositorio.save(hist);
		return hist;
	}
	
	public void cancelarServico(HistoricoServico hist) {
		verificarExistencia(hist);
		hist.setStatus(EstadoServico.CANCELADO);
		historicoServicoRepositorio.save(hist);
	}

	private void verificarExistencia(HistoricoServico hist) {
		buscar(hist.getId());
	}

	public List<HistoricoServico> buscarServicoPorDono(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hs.id, " + 
								 	"hs.servico_id, " + 
									"hs.usuario_requisicao_id, " + 
								 	"hs.status, " + 
									"hs.inicio " + 
									"FROM public.historico_servico hs " +
									"INNER JOIN servico s ON s.id = hs.servico_id " +
								 	"WHERE s.usuario_id = :usuario_id AND hs.status = 'CONTATO_SOLICITADO'",
				parametros, new PreparedStatementCallback<List<HistoricoServico>>() {

					@Override
					public List<HistoricoServico> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoServico> hist = new ArrayList<HistoricoServico>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoServico(resultadoDaConsulta.getString("id"),
													resultadoDaConsulta.getString("servico_id"), 
													resultadoDaConsulta.getString("usuario_requisicao_id"),
													resultadoDaConsulta.getDate("inicio"),
													EstadoServico.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public List<HistoricoServico> buscarServicoStatusAberto(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hs.id, " + 
								 	"hs.servico_id, " + 
									"hs.usuario_requisicao_id, " + 
								 	"hs.status, " + 
									"hs.inicio " + 
									"FROM public.historico_servico hs " +
									"INNER JOIN servico s ON s.id = hs.servico_id " +
								 	"WHERE s.usuario_id = :usuario_id AND hs.status = 'ABERTO'",
				parametros, new PreparedStatementCallback<List<HistoricoServico>>() {

					@Override
					public List<HistoricoServico> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoServico> hist = new ArrayList<HistoricoServico>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoServico(resultadoDaConsulta.getString("id"),
													resultadoDaConsulta.getString("servico_id"), 
													resultadoDaConsulta.getString("usuario_requisicao_id"),
													resultadoDaConsulta.getDate("inicio"),
													EstadoServico.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public List<HistoricoServico> buscarservicoStatusFechamentoSolicitado(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hs.id, " + 
								 	"hs.servico_id, " + 
									"hs.usuario_requisicao_id, " + 
								 	"hs.status, " + 
									"hs.inicio " + 
									"FROM public.historico_servico hs " +
									"INNER JOIN servico s ON s.id = hs.servico_id " +
								 	"WHERE s.usuario_id = :usuario_id AND hs.status = 'FECHAMENTO_SOLICITADO'",
				parametros, new PreparedStatementCallback<List<HistoricoServico>>() {

					@Override
					public List<HistoricoServico> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoServico> hist = new ArrayList<HistoricoServico>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoServico(resultadoDaConsulta.getString("id"),
													resultadoDaConsulta.getString("servico_id"), 
													resultadoDaConsulta.getString("usuario_requisicao_id"),
													resultadoDaConsulta.getDate("inicio"),
													EstadoServico.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public List<HistoricoServico> buscarServicoStatusFechamentoSolicitadoPedido(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hs.id, " + 
								 	"hs.servico_id, " + 
									"hs.usuario_requisicao_id, " + 
								 	"hs.status, " + 
									"hs.inicio " + 
									"FROM public.historico_servico hs " +
									"INNER JOIN servico s ON s.id = hs.servico_id " +
								 	"WHERE hs.usuario_requisicao_id = :usuario_id AND hs.status = 'FECHAMENTO_SOLICITADO'",
				parametros, new PreparedStatementCallback<List<HistoricoServico>>() {

					@Override
					public List<HistoricoServico> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoServico> hist = new ArrayList<HistoricoServico>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoServico(resultadoDaConsulta.getString("id"),
													resultadoDaConsulta.getString("servico_id"), 
													resultadoDaConsulta.getString("usuario_requisicao_id"),
													resultadoDaConsulta.getDate("inicio"),
													EstadoServico.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public List<HistoricoServico> buscarServicoStatusFechado(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hs.id, " + 
								 	"hs.servico_id, " + 
									"hs.usuario_requisicao_id, " + 
								 	"hs.status, " + 
									"hs.inicio " + 
									"FROM public.historico_servico hs " +
									"INNER JOIN servico s ON s.id = hs.servico_id " +
								 	"WHERE s.usuario_id = :usuario_id AND hs.status = 'FECHADO'",
				parametros, new PreparedStatementCallback<List<HistoricoServico>>() {

					@Override
					public List<HistoricoServico> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoServico> hist = new ArrayList<HistoricoServico>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoServico(resultadoDaConsulta.getString("id"),
													resultadoDaConsulta.getString("servico_id"), 
													resultadoDaConsulta.getString("usuario_requisicao_id"),
													resultadoDaConsulta.getDate("inicio"),
													EstadoServico.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public void deletar(String id) {
		try {
			historicoServicoRepositorio.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_HISTORICO_SERVICO_NAO_ENCONTRADO);
		}
	}

}
