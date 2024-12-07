package org.programmer.cafe.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailMessage {
    private String to;  //수신자
    private String subject; //메일 제목
    private String massage; //메일 내용
}
