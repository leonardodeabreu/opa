package com.opa.pedido;

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
public class HistoricoPedidoService {
	
	private static final String MENSAGEM_HISTORICO_PEDIDO_NAO_ENCONTRADO = "O histórico do pedido não foi encontrado!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";

	@Autowired
	private HistoricoPedidoRepository historicoPedidoRepositorio;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<HistoricoPedido> listar() {
		return historicoPedidoRepositorio.findAll();
	}

	public HistoricoPedido buscar(String id) {
		HistoricoPedido hist = null;

		try {
			hist = historicoPedidoRepositorio.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		if (Objects.isNull(hist)) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_HISTORICO_PEDIDO_NAO_ENCONTRADO);
		}

		return hist;
	}

	public HistoricoPedido salvar(HistoricoPedido hist) {

		hist.setStatus(EstadoPedido.CONTATO_SOLICITADO);
		hist.setInicio(new Date());
		hist.setUltimaAlteracao(new Date());
		return historicoPedidoRepositorio.save(hist);
	}

	public HistoricoPedido atualizar(HistoricoPedido hist) {
		verificarExistencia(hist);
		
		if (hist.getStatus().equals(EstadoPedido.CONTATO_SOLICITADO)) {
			hist.setStatus(EstadoPedido.ABERTO);
		} else if (hist.getStatus().equals(EstadoPedido.ABERTO)) {
			hist.setStatus(EstadoPedido.FECHAMENTO_SOLICITADO);
		} else if (hist.getStatus().equals(EstadoPedido.FECHAMENTO_SOLICITADO)) {
			hist.setStatus(EstadoPedido.FECHADO);
		}
		
		hist.setUltimaAlteracao(new Date());
		return historicoPedidoRepositorio.save(hist);
	}
	
	public void cancelarServico(HistoricoPedido hist) {
		verificarExistencia(hist);
		hist.setUltimaAlteracao(new Date());
		hist.setStatus(EstadoPedido.CANCELADO);
		historicoPedidoRepositorio.save(hist);
	}

	private void verificarExistencia(HistoricoPedido hist) {
		buscar(hist.getId());
	}

	public List<HistoricoPedido> buscarPedidoPorDono(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hp.id, " + 
								 	"hp.pedido_id, " + 
									"hp.usuario_requisicao_id, " + 
								 	"hp.status, " + 
									"hp.inicio " + 
									"FROM public.historico_pedido hp " +
									"INNER JOIN pedido p ON p.id = hp.pedido_id " +
								 	"WHERE p.usuario_id = :usuario_id AND hp.status = 'CONTATO_SOLICITADO'",
				parametros, new PreparedStatementCallback<List<HistoricoPedido>>() {

					@Override
					public List<HistoricoPedido> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoPedido> hist = new ArrayList<HistoricoPedido>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoPedido(resultadoDaConsulta.getString("id"),
														 resultadoDaConsulta.getString("pedido_id"), 
														 resultadoDaConsulta.getString("usuario_requisicao_id"),
														 resultadoDaConsulta.getDate("inicio"),
														 EstadoPedido.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public List<HistoricoPedido> pedidoStatusAberto(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hp.id, " + 
								 	"hp.pedido_id, " + 
									"hp.usuario_requisicao_id, " + 
								 	"hp.status, " + 
									"hp.inicio " + 
									"FROM public.historico_pedido hp " +
									"INNER JOIN pedido p ON p.id = hp.pedido_id " +
								 	"WHERE p.usuario_id = :usuario_id AND hp.status = 'ABERTO'",
				parametros, new PreparedStatementCallback<List<HistoricoPedido>>() {

					@Override
					public List<HistoricoPedido> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoPedido> hist = new ArrayList<HistoricoPedido>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoPedido(resultadoDaConsulta.getString("id"),
														 resultadoDaConsulta.getString("pedido_id"), 
														 resultadoDaConsulta.getString("usuario_requisicao_id"),
														 resultadoDaConsulta.getDate("inicio"),
														 EstadoPedido.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	
	public List<HistoricoPedido> listarSomenteStatusContatoSolicitado() {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("status", "CONTATO_SOLICITADO");

		return jdbcTemplate.execute("SELECT " + 
									"hp.id, " + 
								 	"hp.pedido_id, " + 
									"hp.usuario_requisicao_id, " + 
								 	"hp.status, " + 
									"hp.inicio " + 
									"FROM public.historico_pedido hp " +
									"INNER JOIN pedido p ON p.id = hp.pedido_id " +
								 	"WHERE hp.status = :status",
				parametros, new PreparedStatementCallback<List<HistoricoPedido>>() {

					@Override
					public List<HistoricoPedido> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoPedido> hist = new ArrayList<HistoricoPedido>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoPedido(resultadoDaConsulta.getString("id"),
														 resultadoDaConsulta.getString("pedido_id"), 
														 resultadoDaConsulta.getString("usuario_requisicao_id"),
														 resultadoDaConsulta.getDate("inicio"),
														 EstadoPedido.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public List<HistoricoPedido> buscarPedidoStatusFechamentoSolicitado(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hp.id, " + 
								 	"hp.pedido_id, " + 
									"hp.usuario_requisicao_id, " + 
								 	"hp.status, " + 
									"hp.inicio " + 
									"FROM public.historico_pedido hp " +
									"INNER JOIN pedido p ON p.id = hp.pedido_id " +
								 	"WHERE p.usuario_id = :usuario_id AND hp.status = 'FECHAMENTO_SOLICITADO'",
				parametros, new PreparedStatementCallback<List<HistoricoPedido>>() {

					@Override
					public List<HistoricoPedido> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoPedido> hist = new ArrayList<HistoricoPedido>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoPedido(resultadoDaConsulta.getString("id"),
													 	 resultadoDaConsulta.getString("pedido_id"), 
													  	 resultadoDaConsulta.getString("usuario_requisicao_id"),
														 resultadoDaConsulta.getDate("inicio"),
														 EstadoPedido.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public List<HistoricoPedido> buscarPedidoStatusFechado(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"hp.id, " + 
								 	"hp.pedido_id, " + 
									"hp.usuario_requisicao_id, " + 
								 	"hp.status, " + 
									"hp.inicio " + 
									"FROM public.historico_pedido hp " +
									"INNER JOIN pedido p ON p.id = hp.pedido_id " +
								 	"WHERE p.usuario_id = :usuario_id AND hp.status = 'FECHADO'",
				parametros, new PreparedStatementCallback<List<HistoricoPedido>>() {

					@Override
					public List<HistoricoPedido> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<HistoricoPedido> hist = new ArrayList<HistoricoPedido>();

						while (resultadoDaConsulta.next()) {
							hist.add(new HistoricoPedido(resultadoDaConsulta.getString("id"),
													 	 resultadoDaConsulta.getString("pedido_id"), 
													  	 resultadoDaConsulta.getString("usuario_requisicao_id"),
														 resultadoDaConsulta.getDate("inicio"),
														 EstadoPedido.valueOf(resultadoDaConsulta.getString("status"))));
						}

						return hist;
					}
				});
	}
	
	public void deletar(String id) {
		try {
			historicoPedidoRepositorio.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_HISTORICO_PEDIDO_NAO_ENCONTRADO);
		}
	}

}
