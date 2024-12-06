package org.programmer.cafe.domain.user.repository;

import jakarta.transaction.Transactional;
import org.programmer.cafe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndPassword(String email, String password);

    Optional<UserProjection> findProjectionById(Long id);

    Optional<UserProjection> findProjectionByEmail(String email);

    @Modifying
    @Query("UPDATE users u SET u.name = :name, u.password = :password WHERE u.email = :email")
    void updateAllByEmail(@Param("email") String email,
                          @Param("name") String name,
                          @Param("password") String password);
}
