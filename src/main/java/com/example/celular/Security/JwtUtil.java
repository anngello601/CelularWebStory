package com.example.celular.Security;

import com.example.celular.Model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret;
    private final long expiration;

    JwtUtil(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // GENERAR TOKEN A PARTIR DEL USUARIO
    public String generarToken(User user) {

        Date ahora = new Date();
        Date expira = new Date(ahora.getTime() + expiration);

        return Jwts.builder()
                .subject(user.getCorreo())
                .claim("id", user.getId())
                .claim("nombre", user.getNombre())
                .issuedAt(ahora)
                .expiration(expira)
                .signWith(getKey())
                .compact();
    }

    // VALIDAR TOKEN Y OBTENER CLAIMS
    public Claims validarToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    // OBTENER CORREO (SUBJECT) DESDE EL TOKEN
    public String obtenerCorreo(String token) {
        Claims claims = validarToken(token);
        return claims != null ? claims.getSubject() : null;
    }
}
