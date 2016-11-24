package com.opa.pedido;

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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.opa.exceptions.IdentificadorNullException;
import com.opa.exceptions.UsuarioNaoEncontradoException;

@Service
public class PedidoService {

	private static final String MENSAGEM_SERVICO_NAO_ENCONTRADO = "O serviço não foi encontrado!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";

	@Autowired
	private PedidoRepository servicoRepositorio;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<Pedido> listar() {
		return servicoRepositorio.findAll();
	}

	public Pedido buscar(String id) {
		Pedido servico = null;

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

	public Pedido salvar(Pedido pedido) {
		return servicoRepositorio.save(pedido);
	}

	public void deletar(String id) {
		try {
			servicoRepositorio.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_SERVICO_NAO_ENCONTRADO);
		}
	}

	public void atualizar(Pedido servico) {
		verificarExistencia(servico);
		servicoRepositorio.save(servico);
	}

	private void verificarExistencia(Pedido servico) {
		buscar(servico.getId());
	}
	
	public List<Pedido> buscarPedidoPorDono(String idUsuario) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario_id", idUsuario);

		return jdbcTemplate.execute("SELECT " + 
									"p.id, " + 
								 	"p.titulo, " + 
									"p.descricao, " + 
									"p.data_cadastro," + 
								 	"p.fk_id_categoria, " + 
									"p.estado_brasil, " + 
								 	"p.cidade_estado, " + 
								 	"p.usuario_id " + 
									"FROM public.pedido p " +
									"INNER JOIN public.historico_pedido hp ON hp.pedido_id = p.id " +
								 	"WHERE p.usuario_id = :usuario_id AND hp.status = 'CONTATO_SOLICITADO'",
				parametros, new PreparedStatementCallback<List<Pedido>>() {

					@Override
					public List<Pedido> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						List<Pedido> pedido = new ArrayList<Pedido>();
						
						while (resultadoDaConsulta.next()) {
							pedido.add(new Pedido(resultadoDaConsulta.getString("id"),
													resultadoDaConsulta.getString("titulo"), 
													resultadoDaConsulta.getString("descricao"),
													resultadoDaConsulta.getDate("data_cadastro"),
													resultadoDaConsulta.getString("estado_brasil"), 
													resultadoDaConsulta.getString("cidade_estado"),
													resultadoDaConsulta.getString("usuario_id")));
						}

						return pedido;
					}
				});
	}

}