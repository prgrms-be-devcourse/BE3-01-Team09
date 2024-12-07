package org.programmer.cafe.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.entity.QOrder;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class mailTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job updateStatusJob;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    public void testBatchJob() throws Exception {
        // 1. 배치 실행
        JobParameters params = new JobParametersBuilder()
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters();

        JobExecution execution = jobLauncher.run(updateStatusJob, params);

        // 2. 배치 실행 결과 확인
        assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());

        // 3. 데이터베이스 확인
        QOrder qOrder = QOrder.order;
        List<Order> orders = jpaQueryFactory.selectFrom(qOrder)
            .where(qOrder.status.eq(OrderStatus.SHIPPING_STARTED))
            .fetch();

        assertFalse(orders.isEmpty());
        orders.forEach(order -> {
            System.out.println("Updated Order: " + order.getId() + ", Email: " + order.getUser().getEmail());
        });
    }

}
