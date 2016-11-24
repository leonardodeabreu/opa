package com.opa.servico;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/servico")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Servico>> listar() {
		return ResponseEntity.ok(servicoService.recuperarServicosPaginando());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void delete(@PathVariable("id") String id) {
		servicoService.deletar(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") String id) {

		Servico servico = servicoService.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(servico);
	}

	@RequestMapping(value = "/servicoPorDono/{usuario_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarServicoPorDono(@PathVariable("usuario_id") String idUsuario) {

		List<Servico> servico = servicoService.buscarServicoPorDono(idUsuario);

		return ResponseEntity.status(HttpStatus.OK).body(servico);
	}
	
	@RequestMapping(value = "/servicoPorCategoria/{categoria_id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarServicoPorCategoria(@PathVariable("categoria_id") String idCategroia) {

		List<Servico> servico = servicoService.buscarServicoPorCategoria(idCategroia);

		return ResponseEntity.status(HttpStatus.OK).body(servico);
	}

	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> salvar(@Valid @RequestBody Servico servico) {

		servico = servicoService.salvar(servico);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(servico.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Servico servico) {

		servicoService.atualizar(servico);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String singleSave(@RequestParam("file") MultipartFile file) {
		String filePath = null;

		if (!file.isEmpty()) {
			try {
				String extension = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
						file.getOriginalFilename().length());

				if (!extension.equals(".png") && !extension.equals(".PNG") && !extension.equals(".jpg")
						&& !extension.equals(".JPG")) {
					return "{\"msn\": \"false\", \"hash\": \"extension invalid\"}";
				}

				String uuid = UUID.randomUUID().toString();

				filePath = "C:/Git/Opa/Backend/src/main/webapp/imagens/servico/" + uuid + extension;

				byte[] bytes = file.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				buffStream.write(bytes);
				buffStream.flush();
				buffStream.close();
				return "{\"msn\": \"true\", \"hash\": \"/imagens/servico/" + uuid + extension + "\"}";

			} catch (Exception e) {
				return "{\"msn\": \"false\", \"hash\": \"exception\"}";
			}
		} else {
			return "{\"msn\": \"false\", \"hash\": \"no hash\"}";
		}
	}

}