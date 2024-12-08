package org.programmer.cafe.domain.user.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.authority.entity.Authority;
import org.programmer.cafe.domain.basetime.entity.BaseTimeEntity;

@Entity(name = "users")
@NoArgsConstructor
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Authority> authorities = new HashSet<>();

    @Builder
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.authorities = new HashSet<>();
    }

    public void updateAuthority(Authority authority) {
        this.authorities.add(authority);
    }
}