package org.programmer.cafe.domain.user.repository;


import org.programmer.cafe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
