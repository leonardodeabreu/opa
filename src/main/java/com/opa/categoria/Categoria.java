package com.opa.categoria;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.opa.utils.AbstractId;

@Entity
@Table(name = "categoria")
public class Categoria extends AbstractId {

	private String nome;
	private String cor;

	public Categoria() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

}
