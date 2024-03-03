package com.example.mainfile.filter;

import com.example.mainfile.service.TokenService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenValidFilterTest {

    @Autowired
    private TokenValidFilter tokenValidFilter;

    @MockBean
    private TokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void testDoFilterInternal() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("token");
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("myCookie", "token")});
        Authentication authentication = new UsernamePasswordAuthenticationToken("test", "password");
        when(tokenService.fromToken("token")).thenReturn(authentication);

        tokenValidFilter.doFilterInternal(request, response, filterChain);

        assertEquals(SecurityContextHolder.getContext().getAuthentication(), authentication);
    }
}

