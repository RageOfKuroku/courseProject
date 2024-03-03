package com.example.mainfile.filter;

import com.example.mainfile.service.TokenService;
import com.example.mainfile.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterTest {

    @Autowired
    private TokenGenFilter tokenGenFilter;

    @MockBean
    private BCryptPasswordEncoder encoder;


    @MockBean
    private UserService userService;

    @MockBean
    private TokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void testTokenGenFilter() throws ServletException, IOException {
        when(request.getParameter("emailLogin")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        UserDetails userDetails = new User("test", "password", new ArrayList<>());
        when(userService.loadUserByUsername("test")).thenReturn(userDetails);
        when(tokenService.createToken(userDetails)).thenReturn("token");
        when(encoder.matches(anyString(), anyString())).thenReturn(true);

        tokenGenFilter.doFilterInternal(request, response, filterChain);

        verify(response, times(1)).setHeader("Authorization", "token");
    }

}

