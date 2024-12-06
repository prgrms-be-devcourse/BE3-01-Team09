package org.programmer.cafe.facade.changestatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.dto.OrderMapper;
import org.programmer.cafe.domain.order.dto2.UserOrderRequest;
import org.programmer.cafe.domain.order.dto2.UserOrderResponse;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateStatusService {

    private final OrderRepository orderRepository;

    public void updateStatus(Long orderId,
        OrderStatus updateStatus) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));


        OrderStatus newStatus = order.getStatus();

        if (order.getStatus() == OrderStatus.CANCEL) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_CANCELED);
        }

        if (order.getStatus() == OrderStatus.SHIPPING_STARTED && updateStatus == OrderStatus.CANCEL) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_STARTED);
        }

        log.info("Updating status of order {} to {}", orderId, newStatus);

        switch (updateStatus) {
            case COMPLETED:
            case SHIPPING_STARTED:
            case CANCEL:
                order.updateStatus(updateStatus);
                break;
            default:
                throw new BadRequestException(ErrorCode.BAD_REQUEST);
        }

    }

}
