package com.opa.usuario;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/usuarioTelefone")
public class UsuarioTelefoneController {

	@Autowired
	private UsuarioTelefoneService usuarioTelefoneService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") String id) {

		UsuarioTelefone tel = usuarioTelefoneService.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(tel);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody UsuarioTelefone telefone) {
		
		telefone = usuarioTelefoneService.salvar(telefone);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(telefone.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody UsuarioTelefone tel) {

		usuarioTelefoneService.atualizar(tel);

		return ResponseEntity.noContent().build();
	}

}