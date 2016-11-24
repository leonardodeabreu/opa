package com.opa.pedido;

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

@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Pedido>> listar() {
		return ResponseEntity.ok(pedidoService.listar());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") String id) {

		Pedido servico = pedidoService.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(servico);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Pedido salvar(@Valid @RequestBody Pedido pedido) {

		return pedidoService.salvar(pedido);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Pedido servico) {

		pedidoService.atualizar(servico);

		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void delete(@PathVariable("id") String id) {
		pedidoService.deletar(id);
	}

	
	@RequestMapping(value = "/pedidoPorDono/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPedidoPorDono(@PathVariable("usuario_id") String idUsuario) {

		List<Pedido> pedido = pedidoService.buscarPedidoPorDono(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(pedido);
	}

}