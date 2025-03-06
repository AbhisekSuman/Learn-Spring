package com.kodnest.demo.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/api/*")
public class OurFilter implements Filter {

    public OurFilter()  {
        System.err.println("Validation Started...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();

        if (response.isCommitted())
        if (url.equals("/api/res2")) {
            String name = request.getParameter("name");

            if (!name.isEmpty()) {
                System.out.println("validation happening..");
                System.out.println("Filtration Done");
                request.setAttribute("userType", "Student");
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
