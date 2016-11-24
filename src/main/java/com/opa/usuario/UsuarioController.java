package com.opa.usuario;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(UsuarioController.MAPPING)
public class UsuarioController {

	public static final String MAPPING = "/usuario";

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Usuario>> listar() {
		return ResponseEntity.ok(usuarioService.listar());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") String id) {

		Usuario usuario = usuarioService.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(usuario);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Valid @RequestBody Usuario usuario) {

		usuario = usuarioService.salvar(usuario);

		return "{\"msn\": \"true\", \"id\": \"" + usuario.getId() + "\"}";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Usuario usuario) {

		usuarioService.atualizar(usuario);

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

				filePath = "C:/Git/Opa/Backend/src/main/webapp/imagens/usuario/" + uuid + extension;

				byte[] bytes = file.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				buffStream.write(bytes);
				buffStream.flush();
				buffStream.close();
				return "{\"msn\": \"true\", \"hash\": \"/imagens/usuario/" + uuid + extension + "\"}";

			} catch (Exception e) {
				return "{\"msn\": \"false\", \"hash\": \"exception\"}";
			}
		} else {
			return "{\"msn\": \"false\", \"hash\": \"no hash\"}";
		}
	}

	@RequestMapping(value = "/me/{token}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> me(@AuthenticationPrincipal Authentication authentication) {
		return ResponseEntity.ok(usuarioService.buscarUsuarioPorLogin(authentication.getName()));
	}
	
}