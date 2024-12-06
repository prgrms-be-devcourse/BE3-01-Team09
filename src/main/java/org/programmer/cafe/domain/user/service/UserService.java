package org.programmer.cafe.domain.user.service;

import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.user.entity.dto.MyPageSearchRequest;
import org.programmer.cafe.domain.user.entity.MyPageStatus;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.entity.dto.MyPageUpdateResponse;
import org.programmer.cafe.domain.user.repository.UserProjection;
import org.programmer.cafe.exception.MyPageException;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MyPageSearchRequest getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new MyPageException(MyPageStatus.INVALID_ID);
        }
        UserProjection user = userRepository.findProjectionById(id).orElseThrow(() -> new MyPageException(MyPageStatus.USER_NOT_FOUND));
        return new MyPageSearchRequest(user.getId(), user.getName(), user.getEmail());
    }

    //patchUser 사용자 입력 검증
    @Transactional
    public MyPageUpdateResponse patchUser(User user) {
        validateInput(user);

        UserProjection foundUser = findUserByEmail(user.getEmail());
        checkForChanges(user, foundUser);

        return updateUserInfo(user);
    }

    // 웹앱에서 에서 비밀번호 및 아이디 미입력시
    private void validateInput(User user) {
        if (user.getPassword() == null || user.getName().isEmpty()) {
            throw new MyPageException(MyPageStatus.EMPTY_INPUT);
        }
    }

    // db 에서 emil 조회에 실패할 시
    private UserProjection findUserByEmail(String email) {
        return userRepository.findProjectionByEmail(email)
                .orElseThrow(() -> new MyPageException(MyPageStatus.USER_NOT_FOUND));
    }

    // 웹앱 에서 변화된 값 없이 update 요청할 시
    private void checkForChanges(User newUser, UserProjection foundUser) {
        boolean sameName = newUser.getName().equals(foundUser.getName());
        boolean passwordUnchanged = userRepository.existsByEmailAndPassword(newUser.getEmail(), newUser.getPassword());

        if (sameName && passwordUnchanged) {
            throw new MyPageException(MyPageStatus.NO_CHANGE);
        }
    }

    // db 에서 Update 쿼리를 수행
    @Transactional
    protected MyPageUpdateResponse updateUserInfo(User user) {
        try {
            userRepository.updateAllByEmail(user.getEmail(), user.getName(), user.getPassword());

            return new MyPageUpdateResponse(true, "Update Successful",
                    new MyPageSearchRequest(user.getId(), user.getName(), user.getEmail()));
        } catch (Exception e) {
            throw new MyPageException(MyPageStatus.UPDATE_FAILED);
        }
    }
}
