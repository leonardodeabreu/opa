package com.opa.pedido;

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
@RequestMapping(value = "/historicoPedido")
public class HistoricoPedidoController {

	@Autowired
	private HistoricoPedidoService service;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<HistoricoPedido>> listar() {
		return ResponseEntity.ok(service.listarSomenteStatusContatoSolicitado());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") String id) {

		HistoricoPedido hist = service.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}

	@RequestMapping(value = "/pedidoPorDono/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPedidoPorDono(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoPedido> hist = service.buscarPedidoPorDono(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}
	
	@RequestMapping(value = "/pedidoStatusAberto/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> pedidoPorDonoStatusAberto(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoPedido> hist = service.pedidoStatusAberto(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}
	
	@RequestMapping(value = "/pedidoStatusFechamentoSolicitado/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPedidoStatusFechamentoSolicitado(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoPedido> hist = service.buscarPedidoStatusFechamentoSolicitado(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}
	
	@RequestMapping(value = "/pedidoStatusFechado/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPedidoStatusFechado(@PathVariable("usuario_id") String idUsuario) {

		List<HistoricoPedido> hist = service.buscarPedidoStatusFechado(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(hist);
	}

	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> salvar(@Valid @RequestBody HistoricoPedido hist) {

		hist = service.salvar(hist);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(hist.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public HistoricoPedido atualizar(@RequestBody HistoricoPedido hist) {
		hist = service.atualizar(hist);
		return hist;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void delete(@PathVariable("id") String id) {
		service.deletar(id);
	}

}
