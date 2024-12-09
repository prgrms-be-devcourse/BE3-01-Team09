package org.programmer.cafe.domain.authority.repository;

import org.programmer.cafe.domain.authority.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
