package org.programmer.cafe.domain.user.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.programmer.cafe.domain.basetime.entity.BaseTimeEntity;
import org.programmer.cafe.domain.deliveryaddress.entity.DeliveryAddress;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<DeliveryAddress> deliveryAddress;

}