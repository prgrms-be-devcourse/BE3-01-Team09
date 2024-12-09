package org.programmer.cafe.domain.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyPageUpdateRequest {
    private Long id;
    private String name;
    private String email;
    private String newPassword;
    private String currentPassword;
    private String confirmNewPassword;

    public boolean isNewPasswordConfirmed() {
        return newPassword != null && newPassword.equals(confirmNewPassword);
    }

}
