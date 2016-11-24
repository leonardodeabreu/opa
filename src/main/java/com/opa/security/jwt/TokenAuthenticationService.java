package com.opa.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class TokenAuthenticationService {

	private long EXPIRATIONTIME = 1000 * 60 * 60 * 4; // 4 horas
	private String secret = "opaSenhaSecreta";
	private String tokenPrefix = "Bearer";
	private String headerString = "Authorization";

	public void addAuthentication(HttpServletResponse response, String username) {
		// gerando o token.
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
		
		response.addHeader(headerString, tokenPrefix + " " + JWT);
	}
	
	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(headerString);

		if (token != null) {

			String username = Jwts.parser()
								.setSigningKey(secret)
								.parseClaimsJws(token)
								.getBody()
								.getSubject();

			if (username != null) { 
				return new AuthenticatedUser(username);
			}
		}
		return null;
	}

	public String getUserName(String token) {
		if (token != null) {
			// parse the token.
			String username = Jwts.parser()
								.setSigningKey(secret)
								.parseClaimsJws(token)
								.getBody()
								.getSubject();
		
			if (username != null) {
				return username;
			}
		}
		
		return null;
	}
}
