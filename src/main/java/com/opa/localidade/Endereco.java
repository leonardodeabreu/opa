package com.opa.localidade;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.opa.usuario.Usuario;
import com.opa.utils.AbstractId;

@Entity
@Table(name = "endereco")
public class Endereco extends AbstractId {

	private String cep;
	private String logradouro;
	private String numero;
	private String bairro;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_cidade")
	private Cidade cidade;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_usuario")
	private Usuario usuario;

	public Endereco() {
		super();
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
