package com.opa.localidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opa.utils.AbstractId;

@Entity
@Table(name = "cidade")
public class Cidade extends AbstractId {

	private String nome;

	@Column(name = "codigo_ibge")
	private Integer codigoIbge;

	private Integer populacao_2010;
	private Double densidade_demo;
	private String gentilico;
	private Double area;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "estado_id")
	private Estado estado;

	public Cidade() {
		super();
	}
	
	public Cidade(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(Integer codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Integer getPopulacao_2010() {
		return populacao_2010;
	}

	public void setPopulacao_2010(Integer populacao_2010) {
		this.populacao_2010 = populacao_2010;
	}

	public Double getDensidade_demo() {
		return densidade_demo;
	}

	public void setDensidade_demo(Double densidade_demo) {
		this.densidade_demo = densidade_demo;
	}

	public String getGentilico() {
		return gentilico;
	}

	public void setGentilico(String gentilico) {
		this.gentilico = gentilico;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

}
