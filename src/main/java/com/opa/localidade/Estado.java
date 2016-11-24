package com.opa.localidade;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.opa.utils.AbstractId;

@Entity
@Table(name = "estado")
public class Estado extends AbstractId {

	private String nome;
	private String sigla;
	@Column(name = "codigo_ibge")
	private Integer codigoIbge;

	@OneToMany(mappedBy = "estado", fetch = FetchType.LAZY)
	@Transient
	private Set<Cidade> cidades = new HashSet<Cidade>();

	public Estado() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(Integer codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	public Set<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(Set<Cidade> cidades) {
		this.cidades = cidades;
	}

}
