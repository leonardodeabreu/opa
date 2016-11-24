package com.opa.usuario;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opa.utils.AbstractId;

@Entity
@Table(name = "usuario")
public class Usuario extends AbstractId {

	@NotEmpty(message = "Nome é obrigatório!")
	@Size(max = 150, message = "Nome não pode conter mais de 255 caracteres!")
	private String nome;

	private Boolean ativo;

	@NotEmpty(message = "Email é obrigatório!")
	private String email;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_usuario", nullable = false)
	private List<UsuarioTelefone> telefones;

	private String cpf;

	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	@Column(name = "image")
	private String image;

	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cidade")
	private String cidade;

	@NotEmpty(message = "Login é obrigatório!")
	@Size(min = 3, max = 40, message = "Login deve conter entre 4 e 40 caracteres!")
	private String login;

	@NotEmpty(message = "Senha é obrigatório!")
	@Size(min = 4, message = "Senha deve conter no máximo 20 caracteres!")
	private String senha;
	
	public Usuario() {
		super();
		this.ativo = true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<UsuarioTelefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<UsuarioTelefone> telefones) {
		this.telefones = telefones;
	}

}