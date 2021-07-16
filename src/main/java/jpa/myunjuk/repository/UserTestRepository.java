package jpa.myunjuk.repository;

import jpa.myunjuk.domain.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    Optional<UserTest> findByEmail(String email);
}
