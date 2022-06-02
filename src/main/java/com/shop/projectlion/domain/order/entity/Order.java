package com.shop.projectlion.domain.order.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.order.constant.OrderStatus;
import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Builder
    public Order(Member member, LocalDateTime orderTime, OrderStatus orderStatus, List<OrderItem> orderItems) {
        this.member = member;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public static Order createOrder(LocalDateTime orderTime, Member member) {
        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .orderTime(orderTime)
                .orderItems(new ArrayList<>())
                .build();
        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.updateOrder(this);
    }







}
