package com.opa.servico;

public enum EstadoServico {
	
	CONTATO_SOLICITADO("Contato Solicitado"),
	ABERTO("Aberto"), 
	FECHAMENTO_SOLICITADO("Fechamento Solicitado"),
	FECHADO("Fechado"),
	CANCELADO("Cancelado");

	private EstadoServico(String situacao) {
		this.situacao = situacao;
	}

	private String situacao;

	public String getSituacao() {
		return situacao;
	}

}
