package jpa.myunjuk.module.repository;

import jpa.myunjuk.module.model.domain.Characters;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharactersRepository extends JpaRepository<Characters, Long> {
    List<Characters> findByHeightLessThanEqual(double height);
    Optional<Characters> findByHeight(double height);
}
