package com.example.service.search.controller;

import com.example.service.search.config.TokenService;
import com.example.service.search.domain.User;
import com.example.service.search.domain.UserRole;
import com.example.service.search.model.DataLoginDTO;
import com.example.service.search.model.DataToken;
import com.example.service.search.repository.UserRepoistory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    @Autowired
    private UserRepoistory repoistory;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataToken> login(@RequestBody @Valid DataLoginDTO dto){
        var auth = new UsernamePasswordAuthenticationToken(dto.login(),dto.password());
        var user = manager.authenticate(auth);
        return ResponseEntity.ok(new DataToken(tokenService.generatesToken((User) user.getPrincipal())));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataLoginDTO> register(@RequestBody @Valid DataLoginDTO dto, UriComponentsBuilder builder){
        if(this.repoistory.findByLogin(dto.login())!=null)throw new RuntimeException();
        var user = repoistory.save(new User(dto.login(),this.encoder(dto.password()), UserRole.USER));
        var uri = builder.path("auth/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataLoginDTO(user.getLogin(),user.getPassword()));
    }

    private String encoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
