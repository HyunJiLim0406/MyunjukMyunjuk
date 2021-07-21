package jpa.myunjuk.module.repository;

import jpa.myunjuk.module.model.domain.Characters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharactersRepository extends JpaRepository<Characters, Long> {
}
