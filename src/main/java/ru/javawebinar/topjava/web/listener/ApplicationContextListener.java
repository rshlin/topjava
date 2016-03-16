package ru.javawebinar.topjava.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextListener implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContextListener.class);
    private static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";
    public static final String CONTEXT = "context";
    private ClassPathXmlApplicationContext aCtx;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Creating Application Context");
        ServletContext servletContext = sce.getServletContext();
        aCtx = new ClassPathXmlApplicationContext(servletContext.getInitParameter(CONTEXT_CONFIG_LOCATION));
        servletContext.setAttribute(CONTEXT, aCtx);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Closing Application Context");
        aCtx.close();
    }
}
