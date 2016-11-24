package com.opa.servico;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opa.utils.AbstractId;

@Entity
@Table(name = "historico_servico")
public class HistoricoServico extends AbstractId {

	@Column(name = "servico_id")
	private String servico;

	@Column(name = "usuario_requisicao_id")
	private String usuarioRequisicao;

	@Enumerated(EnumType.STRING)
	private EstadoServico status;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date inicio;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date termino;

	@Column(name = "ultima_alteracao")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date ultimaAlteracao;

	public HistoricoServico() {
		super();
	}

	public HistoricoServico(String id, String servicoId, String usuarioRequisicaoId, Date dataInicio, EstadoServico status) {
		this.id = id;
		this.servico = servicoId;
		this.usuarioRequisicao = usuarioRequisicaoId;
		this.inicio = dataInicio;
		this.status = status;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getUsuarioRequisicao() {
		return usuarioRequisicao;
	}

	public void setUsuarioRequisicao(String usuarioRequisicao) {
		this.usuarioRequisicao = usuarioRequisicao;
	}

	public EstadoServico getStatus() {
		return status;
	}

	public void setStatus(EstadoServico status) {
		this.status = status;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

}
