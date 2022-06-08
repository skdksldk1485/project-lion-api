package com.shop.projectlion.web.orderhist.dto;

import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import com.shop.projectlion.domain.order.constant.OrderStatus;
import com.shop.projectlion.domain.order.entity.Order;
import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderHistDto {

    private Long orderId;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private int totalPrice;
    private int totalDeliveryFee;

    private List<OrderItemHistDto> orderItemHistDtos;

    public static OrderHistDto of(Order order) {
        return OrderHistDto.builder()
                .orderId(order.getId())
                .orderTime(order.getOrderTime())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .totalDeliveryFee(order.getTotalDeliveryFee())
                .build();
    }

    @Getter @Setter
    @Builder
    public static class OrderItemHistDto {
        private String itemName;
        private int count;
        private int orderPrice;
        private String imageUrl;

        public static OrderItemHistDto of(OrderItem orderItem, ItemImage itemImage) {
            return OrderItemHistDto.builder()
                    .imageUrl(itemImage.getImageUrl())
                    .orderPrice(orderItem.getOrderPrice())
                    .count(orderItem.getCount())
                    .itemName(orderItem.getItem().getItemName())
                    .build();
        }
    }

}
