package org.generation.bloguinho.repositories;

import java.util.List;
import org.generation.bloguinho.models.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepository extends JpaRepository<Tema, Long>{
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);
 
}
