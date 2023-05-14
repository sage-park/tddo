package com.sage.tddo.authenticationservice.adapter.out.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "authentication")
public class AuthenticationJpaEntity {

    @Id
    private String id;
    private String password;

    public AuthenticationJpaEntity(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
