package com.opa.servico;

import java.net.URI;
import java.util.List;

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
@RequestMapping(value = "/historicoServico")
public class HistoricoServicoController {

	@Autowired
	private HistoricoServicoService service;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<HistoricoServico>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") String id) {

		HistoricoServico hist = service.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}

	@RequestMapping(value = "/servicoPorDono/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarServicoPorDono(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoServico> hist = service.buscarServicoPorDono(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}

	@RequestMapping(value = "/servicoStatusAberto/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarServicoStatusAberto(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoServico> hist = service.buscarServicoStatusAberto(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}

	@RequestMapping(value = "/servicoStatusFechamentoSolicitado/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarservicoStatusFechamentoSolicitado(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoServico> hist = service.buscarservicoStatusFechamentoSolicitado(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}
	
	@RequestMapping(value = "/servicoStatusFechamentoSolicitadoPedido/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarServicoStatusFechamentoSolicitadoPedido(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoServico> hist = service.buscarServicoStatusFechamentoSolicitadoPedido(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}
	
	@RequestMapping(value = "/servicoStatusFechado/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarServicoStatusFechado(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoServico> hist = service.buscarServicoStatusFechado(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}

	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> salvar(@Valid @RequestBody HistoricoServico hist) {

		hist = service.salvar(hist);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(hist.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public HistoricoServico atualizar(@RequestBody HistoricoServico hist) {

		hist = service.atualizar(hist);
		

		return hist;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void delete(@PathVariable("id") String id) {
		service.deletar(id);
	}

}
