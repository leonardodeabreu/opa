package com.opa.pedido;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opa.categoria.Categoria;
import com.opa.utils.AbstractId;

@Entity
@Table(name = "pedido")
public class Pedido extends AbstractId {

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "valor")
	private Double valor;

	@Column(name = "tipo_periodo")
	private String tipo_periodo;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	@Column(name = "estado_brasil")
	private String estado_brasil;
	
	@Column(name = "cidade_estado")
	private String cidade_estado;
	
	@ManyToOne
	@JoinColumn(name = "fk_id_categoria")
	private Categoria categoria;
	
	@Column(name = "usuario_id")
	private String usuario_id;
	
	public Pedido() {
		super();
	}

	public Pedido(String id, String titulo, String descricao, Date dataCadastro, String estado, String cidade, String usuarioId) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataCadastro = dataCadastro;
		this.estado_brasil = estado;
		this.cidade_estado = cidade;
		this.usuario_id = usuarioId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}


	public Categoria getCategoria() {
		return categoria;
	}

	public String getTipoPeriodo() {
		return tipo_periodo;
	}

	public void setTipoPeriodo(String tipo_periodo) {
		this.tipo_periodo = tipo_periodo;
	}

	public String getEstadoBrasil() {
		return estado_brasil;
	}

	public void setEstadoBrasil(String estado_brasil) {
		this.estado_brasil = estado_brasil;
	}

	public String getCidadeEstado() {
		return cidade_estado;
	}

	public void setCidadeEstado(String cidade_estado) {
		this.cidade_estado = cidade_estado;
	}

	public String getUsuarioId() {
		return usuario_id;
	}

	public void setUsuarioId(String usuario_id) {
		this.usuario_id = usuario_id;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
