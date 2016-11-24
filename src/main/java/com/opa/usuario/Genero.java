package com.opa.usuario;

public enum Genero {

	M("Masculino"), F("Feminino"),O("Outros");

	private Genero(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

}