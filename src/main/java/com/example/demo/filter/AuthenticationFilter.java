package com.example.demo.filter;

import com.example.demo.Repository.UserRepository;
import com.example.demo.Services.AuthService;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final AuthService authService;
    private final UserRepository userRepository;

    private static final String ALLOWED_ORIGIN = "http://localhost:5173";

    private static final String[] UNAUTHENTICATED_PATHS = {
        "/api/users/register",
        "/api/auth/login"
    };

    public AuthenticationFilter(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {

        setCORSHeaders(response);

        String requestURI = request.getRequestURI();

        // Allow public paths (with or without trailing slash)
        for (String path : UNAUTHENTICATED_PATHS) {
            if (requestURI.startsWith(path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // Allow OPTIONS preflight
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Extract token
        String token = getAuthTokenFromCookies(request);
        if (token == null || !authService.validateToken(token)) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized: Invalid or missing token");
            return;
        }

        String username = authService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized: User not found");
            return;
        }

        User authenticatedUser = userOptional.get();
        Role role = authenticatedUser.getRole();

        // Admin protection
        if (requestURI.startsWith("/admin/") && role != Role.ADMIN) {
            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                    "Forbidden: Admin access required");
            return;
        }

        request.setAttribute("authenticatedUser", authenticatedUser);
        filterChain.doFilter(request, response);
    }

    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message)
            throws IOException {

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse =
                String.format("{\"error\":\"%s\"}", message.replace("\"", "\\\""));

        response.getWriter().write(jsonResponse);
    }

    private String getAuthTokenFromCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "authToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}