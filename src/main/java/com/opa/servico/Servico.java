package com.opa.servico;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opa.utils.AbstractId;

@Entity
@Table(name = "servico")
public class Servico extends AbstractId {

	@NotEmpty(message = "Título é obrigatório!")
	private String titulo;

	@NotEmpty
	private String descricao;

	@Column(name = "fk_id_categoria")
	private String categoria;

	private Double valor;

	@Temporal(TemporalType.TIME)
	private Date duracao;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "imagem")
	private String image;

	@Column(name = "imagem2")
	private String image2;

	@Column(name = "imagem3")
	private String image3;

	@Column(name = "imagem4")
	private String image4;

	private String cidade;

	private String estado;

	private String periodo;

	@Column(name = "tipo_periodo")
	private String tipoPeriodo;

	@Column(name = "usuario_id")
	private String usuario;

	public Servico() {
		super();
	}

	public Servico(String id, String titulo, String descricao, String imagem, Date dataCadastro, String categoria,
			String estado, String cidade, String usuario) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.image = imagem;
		this.dataCadastro = dataCadastro;
		this.categoria = categoria;
		this.cidade = cidade;
		this.usuario = usuario;
	}

	public Servico(Date date) {
		super();
		this.dataCadastro = date;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDuracao() {
		return duracao;
	}

	public void setDuracao(Date duracao) {
		this.duracao = duracao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}
	
	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}
	
	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getTipoPeriodo() {
		return tipoPeriodo;
	}

	public void setTipoPeriodo(String tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}