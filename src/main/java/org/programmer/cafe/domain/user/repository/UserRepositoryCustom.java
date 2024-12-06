package org.programmer.cafe.domain.user.repository;

import org.programmer.cafe.domain.user.entity.dto.PageUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<PageUserResponse> getUsersWithPagination(Pageable pageable);
}
