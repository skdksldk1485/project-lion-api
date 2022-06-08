package com.shop.projectlion.domain.order.service;

import com.shop.projectlion.domain.order.entity.Order;
import com.shop.projectlion.domain.order.repository.OrderRepository;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void order(Order order) {
        orderRepository.save(order);
    }

    public Page<Order> findOrderWithMember(String email, Pageable pageable) {
        return orderRepository.findOrderWithMember(email, pageable);
    }

    public Order findByOrderIdWithMemberAndOrderItemsAndItem(Long orderId) {
        return orderRepository.findByOrderIdWithMemberAndOrderItemsAndItem(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ORDER_NOT_EXISTS));
    }

}
