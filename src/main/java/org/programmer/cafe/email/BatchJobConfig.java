package org.programmer.cafe.email;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;

import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.entity.QOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {

    private static final Logger log = LoggerFactory.getLogger(BatchJobConfig.class);

    private final JPAQueryFactory jpaQueryFactory;
    private final EmailService emailService;

    public BatchJobConfig(JPAQueryFactory jpaQueryFactory, EmailService emailService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.emailService = emailService;
    }

    @Bean
    public Job updateStatusJob(JobRepository jobRepository,
        PlatformTransactionManager transactionManager) {
        return new JobBuilder("updateStatusJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(updateStatusStep(jobRepository, transactionManager))
            .build();
    }

    @Bean
    public Step updateStatusStep(JobRepository jobRepository,
        PlatformTransactionManager transactionManager) {
        return new StepBuilder("updateStatusStep", jobRepository)
            .<Order, Order>chunk(10, transactionManager)
            .reader(orderItemReader())
            .processor(orderItemProcessor())
            .writer(orderItemWriter(emailService))
            .build();
    }

    @Bean
    public ItemReader<Order> orderItemReader() {
        return new ItemReader<>() {
            private List<Order> orders;
            private int index = 0;

            @Override
            public Order read() {
                if (orders == null) {
                    log.info("Reader: 상태가 COMPLETED인 주문 읽기...");
                    QOrder qOrder = QOrder.order;
                    orders = jpaQueryFactory.selectFrom(qOrder)
                        .where(qOrder.status.eq(OrderStatus.COMPLETED))
                        .fetch();

                    if (orders.isEmpty()) {
                        log.info("COMPLETED인 주문이 없습니다.");
                        return null;
                    }

                    log.info("{}개의 주문을 읽어왔습니다.", orders.size());
                }

                if (index < orders.size()) {
                    return orders.get(index++);
                } else {
                    return null;
                }
            }
        };
    }


    @Bean
    public ItemProcessor<Order, Order> orderItemProcessor() {
        return order -> {
            log.info("주문 ID {}의 상태를 업데이트 중...", order.getId());
            order.updateStatus(OrderStatus.SHIPPING_STARTED);
            log.info("주문 ID {}의 상태가 SHIPPING_STARTED로 변경되었습니다.", order.getId());
            return order;
        };
    }

    @Bean
    public ItemWriter<Order> orderItemWriter(EmailService emailService) {
        return orders -> {
            if (orders.isEmpty()) {
                log.info("처리할 주문이 없습니다.");
                return;
            }

            log.info("{}개의 주문 상태를 업데이트 중...", orders.size());

            // 데이터베이스 상태 업데이트 (Batch Update)
            QOrder qOrder = QOrder.order;
            for (Order order : orders) {
                jpaQueryFactory.update(qOrder)
                    .set(qOrder.status, order.getStatus())
                    .where(qOrder.id.eq(order.getId()))
                    .execute();
            }

            log.info("{}개의 주문 상태가 업데이트되었습니다.", orders.size());

            // 이메일 전송 (Batch Email Sending)
            for (Order order : orders) {
                String emailContent = String.format(
                    """
                    [배송 안내]
                    고객님, 주문하신 상품의 배송이 시작되었습니다.
    
                    상품명: %s
                    결제 금액: %s원
                    배송지: %s %s
                    우편번호: (%s)
                    주문자명: %s
    
                    감사합니다.
                    """,
                    order.getName(),
                    order.getTotalPrice(),
                    order.getAddress(),
                    order.getAddressDetail(),
                    order.getZipcode(),
                    order.getUser().getName()
                );

                EmailMessage emailMessage = EmailMessage.builder()
                    .to(order.getUser().getEmail()) // 사용자 이메일로 전송
                    .subject(String.format("주문번호: %s - 배송이 시작되었습니다.", order.getId()))
                    .massage(emailContent)
                    .build();

                emailService.sendMail(emailMessage);
                log.info("주문 ID {}에 대한 이메일이 {}로 전송되었습니다.", order.getId(), order.getUser().getEmail());
            }

            log.info("{}개의 주문 이메일이 성공적으로 전송되었습니다.", orders.size());
        };
    }

}

