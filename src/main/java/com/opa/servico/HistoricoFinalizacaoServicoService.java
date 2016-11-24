package com.opa.servico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
public class HistoricoFinalizacaoServicoService {
	
	private static final String MENSAGEM_HISTORICO_FINALIZACAO_SERVICO_NAO_ENCONTRADO = "A finalização do histórico do serviço não foi encontrado!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private HistoricoFinalizacaoServicoRepository repo;
	
	public HistoricoFinalizacaoServico buscarHistoricoFinalizacaoDoServico(String idHistoricoServico) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("historico_servico_id", idHistoricoServico);

		return jdbcTemplate.execute("SELECT " + 
									"hfs.id, " + 
								 	"hfs.historico_servico_id, " + 
									"hfs.usuario_id, " + 
								 	"hfs.origem " + 
									"FROM public.historico_servico_finalizacao hfs " +
									"INNER JOIN historico_servico hs ON hs.id = hfs.historico_servico_id " +
								 	"WHERE hs.id = :historico_servico_id",
				parametros, new PreparedStatementCallback<HistoricoFinalizacaoServico>() {

					@Override
					public HistoricoFinalizacaoServico doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						HistoricoFinalizacaoServico finHist = new HistoricoFinalizacaoServico();

						if (resultadoDaConsulta.next()) {
							finHist.setId(resultadoDaConsulta.getString("id"));
							finHist.setHistoricoServicoId(resultadoDaConsulta.getString("historico_servico_id"));
							finHist.setUsuarioId(resultadoDaConsulta.getString("usuario_id"));
							finHist.setOrigem(resultadoDaConsulta.getString("origem"));
						}

						return finHist;
					}
				});
	}
	
	public void deletar(String id) {
		try {
			repo.delete(id);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public HistoricoFinalizacaoServico salvar(HistoricoFinalizacaoServico histFinalizacao) {
		return repo.save(histFinalizacao);
	}
	
	public HistoricoFinalizacaoServico buscar(String id) {
		HistoricoFinalizacaoServico histFin = null;

		try {
			histFin = repo.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		if (Objects.isNull(histFin)) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_HISTORICO_FINALIZACAO_SERVICO_NAO_ENCONTRADO);
		}

		return histFin;
	}
	
	public void atualizar(HistoricoFinalizacaoServico histFin) {
		verificarExistencia(histFin);
		
//		if (hist.getStatus().equals(EstadoServico.CONTATO_SOLICITADO)) {
//			hist.setStatus(EstadoServico.ABERTO);
//		} else if (hist.getStatus().equals(EstadoServico.ABERTO)) {
//			hist.setStatus(EstadoServico.FECHAMENTO_SOLICITADO_PRIMEIRO_A_FECHAR);
//		} else if (hist.getStatus().equals(EstadoServico.FECHAMENTO_SOLICITADO_PRIMEIRO_A_FECHAR)) {
//			hist.setStatus(EstadoServico.FECHAMENTO_SOLICITADO_SEGUNDO_A_FECHAR);
//		} else if (hist.getStatus().equals(EstadoServico.FECHAMENTO_SOLICITADO_SEGUNDO_A_FECHAR)) {
//			hist.setStatus(EstadoServico.FECHADO);
//		}
//		
//		hist.setUltimaAlteracao(new Date());
//		historicoServicoRepositorio.save(hist);
	}
	
	private void verificarExistencia(HistoricoFinalizacaoServico histFin) {
		buscar(histFin.getId());
	}

}
