package com.example.mainfile.filter;

import com.example.mainfile.service.TokenService;
import com.example.mainfile.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
public class TokenGenFilter extends OncePerRequestFilter {

    @Autowired
    private UserService manager;

    @Autowired
    private TokenService service;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = request.getParameter("emailLogin");
        String password = request.getParameter("password");
        if(username != null){
            UserDetails userDetails = manager.loadUserByUsername(username);

            if(encoder.matches(password, userDetails.getPassword())) {
                String token = service.createToken(userDetails);


                response.setHeader("Authorization", token);
                response.addCookie(new Cookie("myCookie", token));
            }
        }

        filterChain.doFilter(request, response);

    }
}

