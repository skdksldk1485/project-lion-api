package com.shop.projectlion.web.orderhist.service;

import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import com.shop.projectlion.domain.itemImage.service.ItemImageService;
import com.shop.projectlion.domain.order.entity.Order;
import com.shop.projectlion.domain.order.service.OrderService;
import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderHistService {

    private final OrderService orderService;
    private final ItemImageService itemImageService;

    public PageImpl<OrderHistDto> getOrderHistDtos(String email, Pageable pageable) {

        // 1. 회원 및 주문 데이터 조회
        Page<Order> orders = orderService.findOrderWithMember(email, pageable);

        // 2. 주문 데이터 dto 변환
        List<OrderHistDto> orderHistDtos = new ArrayList<>();
        for (Order order : orders) {

            // 3. 주문 정보 dto 생성
            OrderHistDto orderHistDto = OrderHistDto.of(order);

            // 4. 주문 상품 dto 리스트 생성
            List<OrderHistDto.OrderItemHistDto> orderItemHistDto = getOrderItemHistDtos(order);
            orderHistDto.setOrderItemHistDtos(orderItemHistDto);

            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<>(orderHistDtos, pageable, orders.getTotalElements());
    }

    private List<OrderHistDto.OrderItemHistDto> getOrderItemHistDtos(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderHistDto.OrderItemHistDto> orderItemHistDto = orderItems.stream().map(orderItem -> {
            ItemImage itemImage = itemImageService.findByItemAndIsRepImage(orderItem.getItem(), true);
            return OrderHistDto.OrderItemHistDto.of(orderItem, itemImage);
        }).collect(Collectors.toList());
        return orderItemHistDto;
    }

    @Transactional
    public void cancelOrder(Long orderId, String email) {
        Order order = orderService.findByOrderIdWithMemberAndOrderItemsAndItem(orderId);

        if(!order.getMember().getEmail().equals(email)) {
            throw new BusinessException(ErrorCode.NOT_MEMBER_ORDER.getMessage());
        }

        order.cancelOrder();
    }

}
