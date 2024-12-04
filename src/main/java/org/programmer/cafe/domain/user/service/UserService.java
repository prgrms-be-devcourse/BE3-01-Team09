package org.programmer.cafe.domain.user.service;

import org.programmer.cafe.domain.user.entity.dto.MypageResult;
import org.programmer.cafe.domain.user.entity.MyPageStatus;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.exception.MyPageException;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MypageResult getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new MyPageException(MyPageStatus.INVALID_ID);
        }
        User user = userRepository.findById(id).orElseThrow(() -> new MyPageException(MyPageStatus.USER_NOT_FOUND));
        return new MypageResult(user.getId(), user.getName(), user.getEmail());
    }
}
