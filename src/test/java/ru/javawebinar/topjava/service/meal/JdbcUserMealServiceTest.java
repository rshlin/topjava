package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

@ActiveProfiles(Profiles.JDBC)
public class JdbcUserMealServiceTest extends UserMealServiceTest {
}
