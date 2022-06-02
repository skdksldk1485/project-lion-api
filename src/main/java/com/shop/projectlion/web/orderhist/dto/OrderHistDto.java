package com.shop.projectlion.web.orderhist.dto;

import com.shop.projectlion.domain.order.constant.OrderStatus;
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

    @Getter @Setter
    @Builder
    public static class OrderItemHistDto {
        private String itemName;
        private int count;
        private int orderPrice;
        private String imageUrl;
    }

}
