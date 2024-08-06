package com.example.service.search.repository;

import com.example.service.search.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoistory extends JpaRepository<User,Long> {
    UserDetails findByLogin(String username);
}
