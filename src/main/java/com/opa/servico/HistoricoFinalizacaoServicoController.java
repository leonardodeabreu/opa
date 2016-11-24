package com.opa.servico;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/historicoFinalizacaoServico")
public class HistoricoFinalizacaoServicoController {
	
	@Autowired
	private HistoricoFinalizacaoServicoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") String id) {

		HistoricoFinalizacaoServico hist = service.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}

	@RequestMapping(value = "/histFinPorServico/{servico_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarServicoPorDono(@PathVariable("servico_id") String idUsuario) {

		HistoricoFinalizacaoServico hist = service.buscarHistoricoFinalizacaoDoServico(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}

	
	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> salvar(@Valid @RequestBody HistoricoFinalizacaoServico histFin) {

		histFin = service.salvar(histFin);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(histFin.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody HistoricoFinalizacaoServico histFin) {

		service.atualizar(histFin);

		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void delete(@PathVariable("id") String id) {
		service.deletar(id);
	}

}
