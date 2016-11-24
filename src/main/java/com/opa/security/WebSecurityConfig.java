package com.opa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.opa.security.jwt.JWTAuthenticationFilter;
import com.opa.security.jwt.JWTLoginFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring()
		.antMatchers("/fonts/**")
		.antMatchers("/imagens/**")
		.antMatchers("/img/**")
		.antMatchers("/js/**")
		.antMatchers("/less/**")
		.antMatchers("/views/**");
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable caching
        http.headers().cacheControl();
        http.csrf().disable() // disable csrf for our requests.
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.POST,"/login").permitAll()
        .antMatchers(HttpMethod.GET,"/estado").permitAll()
        .antMatchers(HttpMethod.GET,"/cidade").permitAll()
        .antMatchers(HttpMethod.GET,"/cidade/cidadePorEstado/{id}").permitAll()
        .antMatchers(HttpMethod.POST,"/usuario").permitAll()
        .antMatchers(HttpMethod.POST,"/usuario/uploadImage").permitAll()
        .antMatchers(HttpMethod.GET,"/usuario/{id}").permitAll()
        .antMatchers(HttpMethod.GET,"/servico").permitAll()
        .antMatchers(HttpMethod.GET,"/servico/{id}").permitAll()
        .antMatchers(HttpMethod.GET,"/pedido").permitAll()
        .antMatchers(HttpMethod.GET,"/pedido/{id}").permitAll()
        .antMatchers(HttpMethod.GET,"/categoria").permitAll()
        .antMatchers(HttpMethod.GET,"/categoria/{id}").permitAll()
        .antMatchers(HttpMethod.GET,"/esqueciminhasenha").permitAll()
        .antMatchers(HttpMethod.POST,"/usuariosenha").permitAll()
        .anyRequest().authenticated()
        .and()
        // We filter the api/login requests
        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
        // And filter other requests to check the presence of JWT in header
        .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }	

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return new CustomAuthenticationManager();
    }

}
