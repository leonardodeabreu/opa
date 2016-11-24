package com.opa.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.opa.utils.AbstractId;

@Entity
@Table(name = "usuario_telefone")
public class UsuarioTelefone extends AbstractId {
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "telefone")
	private Long telefone;

	public UsuarioTelefone() {
		super();
	}

	public UsuarioTelefone(UsuarioTelefone telefone) {
		this.telefone = (Long) telefone.getTelefone();
		this.tipo = telefone.getTipo();
	}

	public UsuarioTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}
}