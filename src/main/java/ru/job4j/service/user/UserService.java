package ru.job4j.service.user;


import ru.job4j.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> save(User todoUser);

    Optional<User> findByLoginPassword(String login, String password);

    boolean update(User user);

    Optional<User> findById(Long id);

    boolean deleteById(Long id);
}
