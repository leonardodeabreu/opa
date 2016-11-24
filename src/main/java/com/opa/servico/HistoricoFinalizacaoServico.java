package com.opa.servico;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.opa.utils.AbstractId;

@Entity
@Table(name = "historico_servico_finalizacao")
public class HistoricoFinalizacaoServico extends AbstractId {

	@Column(name = "historico_servico_id")
	private String historicoServicoId;

	@Column(name = "usuario_id")
	private String usuarioId;

	private String origem;

	public HistoricoFinalizacaoServico() {
		super();
	}

	public HistoricoFinalizacaoServico(String id, String histServicoId, String usuarioId, String origem) {
		this.id = id;
		this.historicoServicoId = histServicoId;
		this.usuarioId = usuarioId;
		this.origem = origem;
		
	}

	public String getHistoricoServicoId() {
		return historicoServicoId;
	}

	public void setHistoricoServicoId(String historicoServicoId) {
		this.historicoServicoId = historicoServicoId;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

}
