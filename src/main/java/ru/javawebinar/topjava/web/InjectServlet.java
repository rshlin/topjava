package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class InjectServlet extends HttpServlet {
    private static final Logger LOG = getLogger(InjectServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        injectFields(config);
    }

    private void injectFields(ServletConfig config) {
        List<Field> fields = collectFields();
        Enumeration<String> parameterNames = config.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            try {
                for (Field field : fields) {
                    if (parameterName.equals(field.getName())) {
                        field.set(this,Class.forName(config.getInitParameter(parameterName)).newInstance());
                    }
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                LOG.error("failed to inject Servlet's field",e);
            }
        }
    }

    private List<Field> collectFields() {
        List<Field> fields = new ArrayList<>();
        Class<?> current = this.getClass();
        while (current != InjectServlet.class) {
            fields.addAll(Arrays.asList(this.getClass().getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }
}
