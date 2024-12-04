package org.programmer.cafe.domain.user.service;

import org.programmer.cafe.domain.user.dto.LoginResult;
import org.programmer.cafe.domain.user.entity.LoginStatus;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.exception.LoginException;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResult authenticateUser(String name, String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new LoginException(LoginStatus.EMPTY_PASSWORD);
        }
        Optional<User> userOptional = userRepository.findByNameAndPassword(name, password);
        if (userOptional.isEmpty()) {
            throw new LoginException(LoginStatus.INVALID_PASSWORD);
        }

        User user = userOptional.get();
        return new LoginResult(LoginStatus.SUCCESS, user);
    }
}
