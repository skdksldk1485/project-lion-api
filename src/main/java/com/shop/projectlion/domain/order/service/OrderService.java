package com.shop.projectlion.domain.order.service;

import com.shop.projectlion.domain.order.entity.Order;
import com.shop.projectlion.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
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

}
