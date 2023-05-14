package com.sage.tddo.authenticationservice.adapter.out.persistence.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
public class AuthorityJpaEntity implements GrantedAuthority{
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String authority;

    public AuthorityJpaEntity(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
