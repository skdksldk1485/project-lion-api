package com.shop.projectlion.domain.order.repository;

import com.shop.projectlion.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o " +
            "from Order o join fetch o.member " +
            "where o.member.email = :email " +
            "order by o.orderTime desc ",
            countQuery = "select count(o) " +
                    "from Order o " +
                    "where o.member.email = :email")
    Page<Order> findOrderWithMember(@Param("email") String email, Pageable pageable);

    @Query("select o " +
            "from Order o join fetch o.member m " +
            "join fetch o.orderItems oi " +
            "join fetch oi.item " +
            "where o.id = :orderId")
    Optional<Order> findByOrderIdWithMemberAndOrderItemsAndItem(@Param("orderId") Long orderId);
}
