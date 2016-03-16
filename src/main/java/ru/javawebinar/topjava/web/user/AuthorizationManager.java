package ru.javawebinar.topjava.web.user;

import ru.javawebinar.topjava.model.User;

public interface AuthorizationManager {
    User get(int id);
    User create(User user);
    User getByMail(String email);
}
