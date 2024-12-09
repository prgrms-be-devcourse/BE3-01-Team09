package org.programmer.cafe.domain.item.repository;


import org.programmer.cafe.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 상품 데이터 JPA 연결 인터페이스
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}
