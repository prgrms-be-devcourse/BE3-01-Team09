package org.programmer.cafe.domain.user.repository;

import org.programmer.cafe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProjection {
    Long getId();
    String getEmail();
    String getName();
}