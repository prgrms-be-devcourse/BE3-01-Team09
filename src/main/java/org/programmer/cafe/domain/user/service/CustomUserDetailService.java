package org.programmer.cafe.domain.user.service;

import org.programmer.cafe.domain.user.entity.CustomUserDetails;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.programmer.cafe.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static org.programmer.cafe.exception.ErrorCode.USER_NOT_FOUND;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return new CustomUserDetails(userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException((USER_NOT_FOUND))));
    }
}
