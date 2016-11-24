package com.opa.usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.opa.exceptions.IdentificadorNullException;
import com.opa.exceptions.UsuarioNaoEncontradoException;
import com.opa.security.jwt.AccountCredentials;

@Service
public class UsuarioService {
	
	private NamedParameterJdbcTemplate jdbcT;
	private static final String MENSAGEM_USUARIO_NAO_ENCONTRADO = "O usuário não foi encontrado!";
	private static final String MENSAGEM_ID_NULL = "ID não pode ser nulo!";

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize() {
		jdbcT = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Usuario> listar() {
		return usuarioRepositorio.findAll();
	}
	
	public String getEmails(String email){
		
		Usuario usuario = usuarioRepositorio.findByEmail(email);
		return usuario == null ? null : usuario.getEmail();
	}
	
	
	
	public Usuario getUsuarioTrocaSenha(String emailUsuario) {
		Usuario usuario = usuarioRepositorio.findByEmail(emailUsuario);
		return usuario == null ? null : usuario;
	}

	

	public Usuario buscar(String id) {
		Usuario usuario = null;

		try {
			usuario = usuarioRepositorio.findOne(id);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new IdentificadorNullException(MENSAGEM_ID_NULL);
		}

		if (Objects.isNull(usuario)) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_USUARIO_NAO_ENCONTRADO);
		}

		return usuario;
	}
	
	public Usuario buscarUsuarioPorLogin(String login) {
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("login", login);

		return jdbcTemplate.execute("select " +
									"u.id," + 
									"u.login," + 
									"u.cidade," + 
									"u.estado," + 
									"u.cpf," + 
									"u.data_cadastro," + 
									"u.data_nascimento," + 
									"u.email," + 
									"u.nome," + 
									"u.image," +
									"u.genero," +
									"u.senha," +
									"ut.telefone," +
									"ut.tipo " +
									"from public.usuario u "+
									"inner join public.usuario_telefone ut on u.id = ut.fk_id_usuario " +
									"where u.login = :login", 
							parametros, new PreparedStatementCallback<Usuario>() {

					@Override
					public Usuario doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						Usuario usr = new Usuario();
						UsuarioTelefone usuTel = new UsuarioTelefone();
						List<UsuarioTelefone> telefones = new ArrayList<>();

						if (resultadoDaConsulta.next()) {
							usr.setId(resultadoDaConsulta.getString("id"));
							usr.setLogin(resultadoDaConsulta.getString("login"));
							usr.setCidade(resultadoDaConsulta.getString("cidade"));
							usr.setEstado(resultadoDaConsulta.getString("estado"));
							usr.setCpf(resultadoDaConsulta.getString("cpf"));
							usr.setDataCadastro(resultadoDaConsulta.getDate("data_cadastro"));
							usr.setDataNascimento(resultadoDaConsulta.getDate("data_nascimento"));
							usr.setEmail(resultadoDaConsulta.getString("email"));
							usr.setNome(resultadoDaConsulta.getString("nome"));
							usr.setSenha(resultadoDaConsulta.getString("senha"));
							usr.setImage(resultadoDaConsulta.getString("image"));
							
							usuTel.setTelefone(resultadoDaConsulta.getLong("telefone"));
							usuTel.setTipo(resultadoDaConsulta.getString("tipo"));
							telefones.add(usuTel);
							usr.setTelefones(telefones);
						}

						return usr;
					}
				});
	}

	public Usuario salvar(Usuario usuario) {
		//usuario.setDataCadastro(new Date());
		return usuarioRepositorio.save(usuario);
	}

	public void deletar(String id) {
		try {
			usuarioRepositorio.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(MENSAGEM_USUARIO_NAO_ENCONTRADO);
		}
	}

	public void atualizar(Usuario usuario) {
		verificarExistencia(usuario);
		usuarioRepositorio.save(usuario);
	}

	private void verificarExistencia(Usuario usuario) {
		buscar(usuario.getId());
	}

	public String login(HashMap<String, ?> login) {
		String query = "SELECT id FROM usuario WHERE login = :usuario and senha = :senha";
		String retorno = "";
		try {
			List<String> rows = jdbcT.queryForList(query, login, String.class);
			if(rows.size() == 1){
				retorno = "{\"msg\": \"true\", \"id\": \""+rows.get(0)+"\"}";
			}else{
				retorno = "{\"msg\": \"false\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public Usuario efetuarLoginComToken(AccountCredentials ac) {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("login", ac.getUsername());
		parametros.put("senha", ac.getPassword());

		return jdbcTemplate.execute("select " + 
									"login, " + 
									"senha " + 
									"from public.usuario where login = :login and senha = :senha", 
							parametros, new PreparedStatementCallback<Usuario>() {

					@Override
					public Usuario doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ResultSet resultadoDaConsulta = ps.executeQuery();
						Usuario usr = new Usuario();

						if (resultadoDaConsulta.next()) {
							usr.setLogin(resultadoDaConsulta.getString("login"));
							usr.setSenha(resultadoDaConsulta.getString("senha"));
						}

						return usr;
					}
				});
	}

	
}