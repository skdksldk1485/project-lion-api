package com.shop.projectlion.domain.order.repository;

import com.shop.projectlion.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
