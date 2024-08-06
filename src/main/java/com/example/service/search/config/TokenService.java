package com.example.service.search.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.service.search.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {
    @Value("$api.token.secret")
    private String key;

    public String generatesToken(User user){
        try {
            var algorithm = Algorithm.HMAC256(key);
            return JWT.create()
                    .withIssuer("RAFAEL")
                    .withSubject(user.getLogin())
                    .withExpiresAt(expire())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("ERROR IN TOKEN GENERATION", exception);
        }
    }

    private Instant expire() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    public String getToken(String token){
        try {
            var algoritmo = Algorithm.HMAC256(key);
            return JWT.require(algoritmo)
                    .withIssuer("RAFAEL")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}
