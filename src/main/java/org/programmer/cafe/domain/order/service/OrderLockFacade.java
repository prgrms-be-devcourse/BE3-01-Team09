package org.programmer.cafe.domain.order.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.dto.CreateOrderRequest;
import org.programmer.cafe.exception.ErrorCode;
import org.programmer.cafe.exception.OrderException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderLockFacade {

    private ConcurrentHashMap<String, Lock> locks = new ConcurrentHashMap<>();

    private final OrderService orderService;

    // TODO: Critical Section
    // 현재는 동시성 이슈가 발생할 수 있는 영역 전체를 감싸서 성능 저하가 발생할 것임.
    // 하지만, 시간 관계상 현재의 방식으로 구현했으며, 개선이 필요한 영역.
    // 또한 현재의 방식은, 다중 서버 환경에서는 동시성 이슈 발생 가능함.
    public void order(Long userId, CreateOrderRequest createOrderRequest)
        throws InterruptedException {
        Lock lock = locks.computeIfAbsent(String.valueOf(userId), key -> new ReentrantLock());
        boolean acquiredLock = lock.tryLock(3, TimeUnit.SECONDS);
        if (!acquiredLock) {
            throw new OrderException(ErrorCode.FAILED_TO_ACQUIRE_LOCK);
        }
        try {
            orderService.createOrder(userId, createOrderRequest);
        } finally {
            lock.unlock();
        }
    }
}
