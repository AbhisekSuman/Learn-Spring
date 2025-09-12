package com.kodnest.salessavvy.filter;

//import com.kodnest.salessavvy.entities.Role;
//import com.kodnest.salessavvy.entities.User;
//import com.kodnest.salessavvy.repository.UserRepository;
//import com.kodnest.salessavvy.services.AuthService;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//@WebFilter(urlPatterns = {"/api/*,/admin/*"})
//@Component
//public class AuthenticationFilter implements Filter  {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
//    private final AuthService service;
//    private final UserRepository repository;
//
//    public static final String[] UNAUTHENTICATED_PATHS = {
//      "/api/user/register",
//      "/api/authentication/login"
//    };
//
//    public AuthenticationFilter(AuthService service, UserRepository repository) {
//        System.out.println("Filtration Started");
//        this.service = service;
//        this.repository = repository;
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        try {
//            executeFilterLogin(servletRequest, servletResponse, filterChain);
//        } catch (Exception e) {
//            logger.error("Unexpected error: {}", e.getMessage());
//            HttpServletResponse response = (HttpServletResponse) servletResponse;
//            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
//        }
//    }
//
//
//
//    public void executeFilterLogin(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
////        Validation
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse servletResponse = (HttpServletResponse) response;
//
//        String uri = httpServletRequest.getRequestURI();
//
//        if (Arrays.asList(UNAUTHENTICATED_PATHS).contains(uri)) {
//            chain.doFilter(request, response);
//        } else {
//            try {
//                String token = getAuthTokenFromCookie(httpServletRequest);
//                System.out.println(token);
//
//                if (token == null || !service.validateToken(token)) {
//                    sendErrorResponse(servletResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized token");
//                    return;
//                }
//
//                String userName = service.extractUsername(token);
//                User user = repository.findByUsername(userName);
//                if (user == null) {
//                    sendErrorResponse(servletResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized User");
//                    return;
//                }
//
//                Role role = user.getRole();
//
//                if (uri.startsWith("/api/") && (role != Role.ADMIN && role != Role.CUSTOMER)) {
//                    sendErrorResponse(servletResponse, HttpServletResponse.SC_FORBIDDEN, "NOT ALLOWED FOR THIS ROLE");
//                    return;
//                }
//
//                if (uri.startsWith("/admin/") && role != Role.ADMIN) {
//                    sendErrorResponse(servletResponse, HttpServletResponse.SC_FORBIDDEN, "NOT ALLOWED FOR THIS ROLE");
//                    return;
//                }
//
//                httpServletRequest.setAttribute("authenticatedUser", user);
//                chain.doFilter(request, response);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public String getAuthTokenFromCookie(HttpServletRequest request) throws RuntimeException {
//        Cookie[] cookies = request.getCookies();
//        System.out.println(cookies.length);
//        if (cookies != null && cookies.length > 0) {
//            return cookies[0].getValue();
//        }
//        else {
//            throw new RuntimeException("Invalid Cookie");
//        }
//    }
//
//    public void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
//        response.setStatus(status);
//        response.getWriter().write(message);
//    }
//
//    public void setCORSHeaders(HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//
//}



import com.kodnest.salessavvy.entities.Role;
import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.repository.UserRepository;
import com.kodnest.salessavvy.services.AuthService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@WebFilter(urlPatterns = {"/api/", "/admin/"})
@Component
public class AuthenticationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final AuthService authService;
    private final UserRepository userRepository;

    private static final String ALLOWED_ORIGIN = "http://localhost:5173";

    private static final String[] UNAUTHENTICATED_PATHS = {
            "/api/user/register",
            "/api/authentication/login"
    };

    public AuthenticationFilter(AuthService authService, UserRepository userRepository) {
        System.out.println("Filtration Started.");
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            executeFilterLogic(request, response, chain);
        } catch (Exception e) {
            logger.error("Unexpected error in AuthenticationFilter", e);
            sendErrorResponse((HttpServletResponse) response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Internal server error");
        }
    }

    private void executeFilterLogic(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        logger.info("Request URI: {}", requestURI);

        // Allow unauthenticated paths
        if (Arrays.asList(UNAUTHENTICATED_PATHS).contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Handle preflight (OPTIONS) requests
        if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            setCORSHeaders(httpResponse);
            return;
        }

        // Extract and validate the token
        String token = getAuthTokenFromCookies(httpRequest);
        System.out.println(token);
        if (token == null || !authService.validateToken(token)) {
            sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
            return;
        }

        // Extract username and verify user
        String username = authService.extractUsername(token);


        // Get authenticated user and role
        User authenticatedUser = userRepository.findByUsername(username);
        if (authenticatedUser == null) {
            sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: User not found");
            return;
        }
        Role role = authenticatedUser.getRole();
        logger.info("Authenticated User: {}, Role: {}", authenticatedUser.getUsername(), role);

        // Role-based access control
        if (requestURI.startsWith("/admin/") && role != Role.ADMIN) {
            sendErrorResponse(httpResponse, HttpServletResponse.SC_FORBIDDEN, "Forbidden: Admin access required");
            return;
        }

        if (requestURI.startsWith("/api/") && role != Role.CUSTOMER) {
            sendErrorResponse(httpResponse, HttpServletResponse.SC_FORBIDDEN, "Forbidden: Customer access required");
            return;
        }

        // Attach user details to request
        httpRequest.setAttribute("authenticatedUser", authenticatedUser);
        chain.doFilter(request, response);
    }

    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.getWriter().write(message);
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

