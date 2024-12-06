package org.programmer.cafe;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
/*페이징 구현이 직렬화되지 않아
JSON 형식의 body에 안정성이 없다는 경고가 떠서 추가*/
public class CafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeApplication.class, args);
    }

}
