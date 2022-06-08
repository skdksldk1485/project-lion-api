package com.shop.projectlion.domain.orderItem.repository;

import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
