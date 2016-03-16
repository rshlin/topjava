package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private final AtomicInteger counter = new AtomicInteger(0);

    private final ConcurrentHashMap<Integer, User> repository = new ConcurrentHashMap<>();

    {
        save(new User(0, "admin", "admin@gmail.com", "password", Role.ROLE_ADMIN, Role.ROLE_USER));
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            LOG.trace("Generating id");
            user.setId(counter.incrementAndGet());
        }
        LOG.info("Saving {}", user);
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        LOG.info("Deleting User: id[{}]", id);
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        LOG.info("Retrieving User: id[{}]", id);
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("Retrieving User: email[{}]", email);
        return
                repository.values()
                        .stream()
                        .filter(user -> user.getEmail().equalsIgnoreCase(email))
                        .findFirst()
                        .orElse(null);
    }

    @Override
    public List<User> getAll() {
        LOG.info("Retrieving all Users");
        return
                repository.values()
                        .stream()
                        .sorted(Comparator.comparing(User::getName))
                        .collect(Collectors.toList());
    }
}
