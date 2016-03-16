package ru.javawebinar.topjava.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationFilter.class);

    public static final String USER_PROFILE = "profile";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(true);

        if (null == session.getAttribute(USER_PROFILE)) {
            LOG.info("{} arrived", request.getRemoteAddr());
            ((HttpServletResponse) response).sendRedirect("register");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
