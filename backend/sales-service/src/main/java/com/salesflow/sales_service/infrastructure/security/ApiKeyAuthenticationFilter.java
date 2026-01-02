package com.salesflow.sales_service.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final String expectedSecret;

    public ApiKeyAuthenticationFilter(String expectedSecret) {
        this.expectedSecret = expectedSecret;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return uri.startsWith("/actuator")
                || uri.startsWith("/v3/api-docs")
                || uri.startsWith("/swagger-ui");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.equals(expectedSecret)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var auth = new UsernamePasswordAuthenticationToken(
                "api-user",
                null,
                List.of()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
