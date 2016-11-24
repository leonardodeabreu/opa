package com.opa.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import com.opa.usuario.Usuario;
import com.opa.usuario.UsuarioController;
import com.opa.usuario.UsuarioRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class UsuarioControllerTest extends ControllerTest {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Test
	@Rollback
	public void testSave() throws Exception {
		final Usuario usuEsperado = new Usuario();
		usuEsperado.setLogin("leonardo");
		usuEsperado.setSenha("123456");

		final Usuario usuario = new Usuario();
		usuario.setLogin("leonardo");
		usuario.setSenha("123456");

		final MvcResult result = post(UsuarioController.MAPPING, usuario).andExpect(status().isCreated()).andReturn();

		final Usuario usuPersistido = (Usuario) parseJson(result, Usuario.class);
		Assert.assertEquals("persistencia deu ruim", usuario, usuPersistido);
	}

}