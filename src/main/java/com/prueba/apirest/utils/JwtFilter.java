package com.prueba.apirest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final JwtToken jwtToken;

    public JwtFilter(JwtUtil jwtUtil, JwtToken jwtToken) {
        this.jwtUtil = jwtUtil;
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/api/producto/listar") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        Map<String, Object> body = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (request.getRequestURI().equals("/api/producto/listar")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (!jwtToken.contains(token)) {
                body.put("mensaje", "Token inválido o expirado");
                body.put("code", HttpStatus.UNAUTHORIZED.value());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().write(mapper.writeValueAsString(body));
                //response.getWriter().write("Token inválido o expirado");
                return;
            }
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            body.put("mensaje", "Se requiere token");
            body.put("code", HttpStatus.UNAUTHORIZED.value());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(body));
            //response.getWriter().write("Se requiere token");
            return;
        }
        String token = authHeader.substring(7);

        if (!jwtToken.contains(token)) {
            body.put("mensaje", "Token inválido o expirado");
            body.put("code", HttpStatus.UNAUTHORIZED.value());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(body));

            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //response.getWriter().write("Token inválido o expirado");
            return;
        }
        if (jwtToken.contains(token)){
            String username = jwtUtil.extractUsername(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            body.put("mensaje", "Token inválido o expirado");
            body.put("code", HttpStatus.UNAUTHORIZED.value());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(body));

            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //response.getWriter().write("Token inválido o expirado");
            return;
        }
        filterChain.doFilter(request, response);
    }
}