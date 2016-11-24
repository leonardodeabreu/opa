package com.opa.localidade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CidadeController.MAPPING)
public class CidadeController {

	public static final String MAPPING = "/cidade";

	@Autowired
	private CidadeService service;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Cidade>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") String id) {

		Cidade cidade = service.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(cidade);
	}
	
	@RequestMapping(value = "/cidadePorEstado/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Cidade>> listarCidadePeloEstado(@PathVariable("id") String id) {
		
		return ResponseEntity.ok(service.recuperarCidadePorEstado(id));
	}

}
