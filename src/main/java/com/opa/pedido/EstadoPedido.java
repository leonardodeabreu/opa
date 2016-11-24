package com.opa.pedido;

public enum EstadoPedido {
	
	CONTATO_SOLICITADO("Contato Solicitado"),
	ABERTO("Aberto"), 
	FECHAMENTO_SOLICITADO("Fechamento Solicitado"),
	FECHADO("Fechado"),
	CANCELADO("Cancelado");

	private EstadoPedido(String situacao) {
		this.situacao = situacao;
	}

	private String situacao;

	public String getSituacao() {
		return situacao;
	}

}
