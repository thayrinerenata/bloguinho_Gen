package org.generation.bloguinho.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;
import org.generation.bloguinho.models.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){
		
		usuarioRepository.save(new Usuario(0L, "Tarsila Silva", "tarsila@email.com", "12345", "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Leonardo Silva", "leonardodv@email.com", "12345", "https://i.imgur.com/mB3VM2N.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Salvador Silva", "salvador@email.com", "12345", "https://i.imgur.com/JR7kUFU.jpg"));

	}
	
	@Test
	@DisplayName("Retorna 1 usuário")
	public void deveRetornarUmUsuario() {
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("tarsila@email.com");
		assertTrue(usuario.get().getUsuario().equals("tarsila@email.com"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuário")
	public void deveRetornarTresUsuario() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Tarsila Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Leonardo Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Salvador Silva"));
	}
	
	


}
