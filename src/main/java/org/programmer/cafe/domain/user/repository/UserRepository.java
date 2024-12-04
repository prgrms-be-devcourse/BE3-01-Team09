package org.programmer.cafe.domain.user.repository;
import org.programmer.cafe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameAndPassword(String name, String password);
    Optional<User> findById(Long id);
}
