package com.example.mainfile.filter;


import com.example.mainfile.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenValidFilter extends OncePerRequestFilter {
    private final TokenService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(request.getCookies() != null) {
            Optional<String> authorization = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("myCookie"))
                    .findFirst()
                    .map(Cookie::getValue);

            if (authorization.isPresent()) {
                Authentication authentication = service.fromToken(authorization.get());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println();
            }
        }
        filterChain.doFilter(request, response);
    }
}
