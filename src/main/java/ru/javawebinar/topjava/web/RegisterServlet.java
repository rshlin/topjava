package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.filter.AuthorizationFilter;
import ru.javawebinar.topjava.web.listener.ApplicationContextListener;
import ru.javawebinar.topjava.web.user.AuthorizationManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

public class RegisterServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterServlet.class);
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String CALORIES_PER_DAY = "caloriesPerDay";
    public static final String EMAIL = "email";

    @Autowired
    AuthorizationManager manager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ((ApplicationContext) config.getServletContext().getAttribute(ApplicationContextListener.CONTEXT))
                .getAutowireCapableBeanFactory()
                .autowireBean(this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(EMAIL);
        boolean userExist = true;
        try {
            manager.getByMail(email);
        } catch (NotFoundException e) {
            userExist = false;
        }
        if (userExist) {
            req.setAttribute("error", EMAIL);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        try {
            User user = manager.create(new User(
                    null,
                    req.getParameter(NAME),
                    email,
                    req.getParameter(PASSWORD),
                    Integer.parseInt(req.getParameter(CALORIES_PER_DAY)),
                    true,
                    EnumSet.of(Role.ROLE_USER)
            ));
            req.getSession().setAttribute(AuthorizationFilter.USER_PROFILE, new LoggedUser(user));
            resp.sendRedirect("meals");
        } catch (NumberFormatException e) {
            LOG.error("Invalid input data", e);
            req.setAttribute("error", CALORIES_PER_DAY);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }
}
