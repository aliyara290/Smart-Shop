package com.aliyara.smartshop.filter;

import com.aliyara.smartshop.enums.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();

        if (path.equals("/api/v1/auth/login")) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("You are not logged in");
            return;
        }

        UserRole role = (UserRole) session.getAttribute("ROLE");

        if (role == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User role not found in session");
            return;
        }


        if (path.matches("/api/v1/admin/.*")) {

            if (role != UserRole.ADMIN) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access denied: ADMIN only");
                return;
            }
        }

        if (path.matches("/api/v1/client-account/.*")) {
            if (role != UserRole.CLIENT) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access denied: Clients only");
                return;
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }
}
