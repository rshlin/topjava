package ru.javawebinar.topjava.repository.mock;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import static org.junit.Assert.*;

public class InMemoryUserRepositoryImplTest {

    private InMemoryUserRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository = new InMemoryUserRepositoryImpl();
    }

    @Test
    public void testDelete() throws Exception {
        User user = repository.save(new User(null, "user", "user@gmail.com", "password", Role.ROLE_USER));
        assertTrue(repository.delete(user.getId()));

    }

    @Test
    public void testDeleteInvalidUser() throws Exception {
        assertFalse(repository.delete(55));
    }

    @Test
    public void testGet() throws Exception {
        User user = repository.save(new User(null, "user", "user@gmail.com", "password", Role.ROLE_USER));
        assertEquals(repository.get(user.getId()), user);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = repository.save(new User(null, "user", "user@gmail.com", "password", Role.ROLE_USER));
        assertEquals(repository.getByEmail("user@gmail.com"), user);
    }

    @Test
    public void testGetByEmailInvalidUser() throws Exception {
        assertNull(repository.getByEmail("user@gmail.com"));
    }

}