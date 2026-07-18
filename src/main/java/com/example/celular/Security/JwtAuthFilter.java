package com.example.celular.Security;

import com.example.celular.Model.User;
import com.example.celular.Repository.UserRepository;

import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    JwtAuthFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        // SI LA SESIÓN YA TIENE AL USUARIO, NO HAY NADA QUE HACER
        if (session.getAttribute("usuario") == null) {

            String token = obtenerTokenDeCookie(request);

            if (token != null) {
                Claims claims = jwtUtil.validarToken(token);

                if (claims != null) {
                    User usuario = userRepository.findByCorreo(claims.getSubject());
                    if (usuario != null) {
                        session.setAttribute("usuario", usuario);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String obtenerTokenDeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
