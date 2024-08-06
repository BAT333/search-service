package com.example.service.search.service;

import com.example.service.search.repository.UserRepoistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepoistory repoistory;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repoistory.findByLogin(username);
    }
}
