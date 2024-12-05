package org.programmer.cafe.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.basetime.entity.BaseTimeEntity;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    private String password;

    @Column(nullable = false, length = 10)
    private String name;

}