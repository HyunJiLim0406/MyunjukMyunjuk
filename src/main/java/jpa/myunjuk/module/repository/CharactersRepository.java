package jpa.myunjuk.module.repository;

import jpa.myunjuk.module.model.domain.Characters;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharactersRepository extends JpaRepository<Characters, Long> {
    List<Characters> findByHeightLessThanEqualAndIdNotInOrderByHeightDesc(double height, List<Long> id);
    Characters findFirstByHeightLessThanEqualOrderByHeightDesc(double height);
}
