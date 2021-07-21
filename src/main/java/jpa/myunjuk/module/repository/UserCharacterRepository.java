package jpa.myunjuk.module.repository;

import jpa.myunjuk.module.model.domain.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {
}
