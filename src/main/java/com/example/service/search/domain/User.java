package com.example.service.search.domain;

import com.example.service.search.model.DataLoginDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "logins")
    private String login;
    @Column(name = "passwords")
    private String passwords;
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String login, String encoder, UserRole userRole) {
        this.login = login;
        this.passwords = encoder;
        this.role = userRole;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_SUB_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_SUB"));
        } else if (this.role == UserRole.SUB_ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_SUB_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_SUB"));
        }else if(this.role == UserRole.USER){
            return List.of(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_SUB"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_SUB"));
    }

    @Override
    public String getPassword() {
        return this.passwords;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
