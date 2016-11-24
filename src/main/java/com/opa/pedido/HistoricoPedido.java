package com.opa.pedido;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opa.utils.AbstractId;

@Entity
@Table(name = "historico_pedido")
public class HistoricoPedido extends AbstractId {

	@Column(name = "pedido_id")
	private String pedido;

	@Column(name = "usuario_requisicao_id")
	private String usuarioRequisicao;

	@Enumerated(EnumType.STRING)
	private EstadoPedido status;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date inicio;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date termino;

	@Column(name = "ultima_alteracao")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date ultimaAlteracao;

	public HistoricoPedido() {
		super();
	}

	public HistoricoPedido(String id, String pedidoId, String usuarioRequisicaoId, Date dataInicio, EstadoPedido status) {
		this.id = id;
		this.pedido = pedidoId;
		this.usuarioRequisicao = usuarioRequisicaoId;
		this.inicio = dataInicio;
		this.status = status;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getUsuarioRequisicao() {
		return usuarioRequisicao;
	}

	public void setUsuarioRequisicao(String usuarioRequisicao) {
		this.usuarioRequisicao = usuarioRequisicao;
	}

	public EstadoPedido getStatus() {
		return status;
	}

	public void setStatus(EstadoPedido status) {
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
