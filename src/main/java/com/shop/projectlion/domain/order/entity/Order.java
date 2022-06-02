package com.shop.projectlion.domain.order.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.delivery.entity.Delivery;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int getTotalPrice() {
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        long totalDeliveryFee = getTotalDeliveryFee();
        totalPrice += totalDeliveryFee;
        return totalPrice;
    }

    public int getTotalDeliveryFee() {
        int totalDeliveryFee = 0;
        Map<Long, Delivery> deliveryMap = new HashMap<>();
        for(OrderItem orderItem : orderItems) {
            Delivery delivery = orderItem.getItem().getDelivery();
            deliveryMap.put(delivery.getId(), delivery);
        }

        // 배송비 추가 (배송비 아이디가 같은 경우 출고지가 같은걸로보고 배송비를 1번만 적용함)
        for (Long deliveryId : deliveryMap.keySet()) {
            Delivery delivery = deliveryMap.get(deliveryId);
            totalDeliveryFee += delivery.getDeliveryFee();
        }

        return totalDeliveryFee;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }







}
