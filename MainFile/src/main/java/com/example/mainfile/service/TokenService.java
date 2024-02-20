package com.example.mainfile.service;

import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Value(value = "${project.secretKey}")
    private String keyValue;

    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        secretKey = Keys.hmacShaKeyFor(keyValue.getBytes());
    }
    public String createToken(UserDetails userDetails){
        var user = (UserEntity)userDetails;
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder().subject(user.getEmail())
                .claim("username", user.getEmail())
                .claim("roles", roles)
                .claim("name", user.getName())
                .claim("id", String.valueOf(user.getId()))
                .claim("phoneNumber", user.getPhoneNumber())
                .signWith(secretKey)
                .compact();
    }
    public Authentication fromToken(String token){
        JwtParser parser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();

        Claims payload = (Claims) parser.parse(token).getPayload();

        String username = (String) payload.get("username");
        String name = (String) payload.get("name");
        UUID id = UUID.fromString((String) payload.get("id"));
        String phoneNumber = (String) payload.get("phoneNumber");
        String roles = (String) payload.get("roles");

        UserEntity build = UserEntity.builder()
                .id(id)
                .name(name)
                .email(username)
                .phoneNumber(phoneNumber)
                .role(Role.valueOf(roles))
                .build();

        List<SimpleGrantedAuthority> list = Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(build, null, list);
    }
}