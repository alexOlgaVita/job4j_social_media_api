package ru.job4j.service.user;

import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.ofNullable(userRepository.save(user));
    }

    @Override
    public Optional<User> findByLoginPassword(String login, String password) {
        return userRepository.findByLoginPassword(login, password);
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user) > 0L;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        return userRepository.delete(id) > 0L;
    }
}