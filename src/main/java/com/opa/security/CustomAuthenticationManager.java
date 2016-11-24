package com.opa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.opa.security.jwt.AccountCredentials;
import com.opa.usuario.Usuario;
import com.opa.usuario.UsuarioService;

public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired
	private UsuarioService service;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		try {
			AccountCredentials ac = new AccountCredentials();
			ac.setUsername(auth.getPrincipal().toString());
			ac.setPassword(auth.getCredentials().toString());
			Usuario user = service.efetuarLoginComToken(ac);
			
			if (auth.getCredentials().toString().equals(user.getSenha())) {
				return auth;
			}
		} catch (NullPointerException e){
			throw new BadCredentialsException("Usuário não cadastrado!");
		}
		throw new BadCredentialsException("Senha incorreta!");
	}

}