package com.gymapplication.club_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String secretKey = "ThisIsOurVeryVeryVeryVeryHardPrivateKey";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromCookie(request.getCookies());

        try {
            if (token != null) {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey.getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                Date expirationDate = claims.getExpiration();
                if (expirationDate != null && expirationDate.before(new Date())) {
                    if (!response.isCommitted()) {
                        Cookie cookie = new Cookie("tokenError", "true");
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        response.sendRedirect("http://localhost:8080/lr/login");
                    }
                    return;
                }

                filterChain.doFilter(request, response);
            }
        } catch (JwtException e) {
            if (!response.isCommitted()) {
                Cookie cookie = new Cookie("tokenError", "true");
                cookie.setPath("/");
                response.addCookie(cookie);
                response.sendRedirect("http://localhost:8080/lr/login");
            }
            return;
        }

        if (!response.isCommitted()) {
            Cookie cookie = new Cookie("tokenError", "true");
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("http://localhost:8080/lr/login");
        }
    }


    private String extractTokenFromCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
