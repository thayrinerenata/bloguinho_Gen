package org.generation.bloguinho.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.generation.bloguinho.models.UserLogin;
import org.generation.bloguinho.models.Usuario;
import org.generation.bloguinho.repositories.UsuarioRepository;
import org.generation.bloguinho.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user) {
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> Post(@Valid @RequestBody Usuario usuario) {
		return usuarioService.CadastrarUsuario(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		}
	
	@GetMapping("/buscar/tudo")
	public ResponseEntity<List<Usuario>> buscarTodosUsuarios(){
		List<Usuario> list = usuarioRepository.findAll();
		
		if (list.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(list);
		}
	}
	
	@GetMapping("/buscar/{idUsuario}")
	public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable(value = "idUsuario") Long idUsuario) {
		return usuarioRepository.findById(idUsuario)
				.map(resp -> ResponseEntity.status(200).body(resp))
				.orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado");
				});
	}
	
	@GetMapping("/buscar/email/{emailUsuario}")
	public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable(value = "emailUsuario") String emailUsuario) {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(emailUsuario);		
		if (usuario.isPresent()) {
			return ResponseEntity.status(200).build();
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não foi encontrado");
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.findById(usuario.getId())
				.map(resp -> ResponseEntity.status(200).body(usuarioRepository.save(usuario)))
				.orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado");
				});
	}
	
	@DeleteMapping("/deletar/{idUsuario}")
	public ResponseEntity<Usuario> deletarUsuario(@PathVariable(value = "idUsuario") Long idUsuario) {
		Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
		
		if (usuario.isPresent()) {
			usuarioRepository.deleteById(idUsuario);
			return ResponseEntity.status(200).build();
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não foi encontrado");
		}
	}

}
