package org.generation.bloguinho.repositories;

import java.util.Optional;
import org.generation.bloguinho.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	public Optional<Usuario> findByUsuario(String usuario);
	

}
