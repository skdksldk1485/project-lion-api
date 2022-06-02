package com.shop.projectlion.web.orderhist.controller;

import com.shop.projectlion.domain.order.constant.OrderStatus;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderhist")
public class OrderHistController {

    @GetMapping
    public String orderHist(Optional<Integer> page, Model model, Principal principal) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);

        // 주문 정보
        List<OrderHistDto> orderHistDtos = new ArrayList<>();
        OrderHistDto orderHistDto = OrderHistDto.builder()
                .orderId(1L)
                .orderTime(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .totalPrice(13000)
                .totalDeliveryFee(3000)
                .build();
        orderHistDtos.add(orderHistDto);

        // 주문 상품 정보
        List<OrderHistDto.OrderItemHistDto> orderItemHistDtos = new ArrayList<>();
        for(int i=1;i<2;i++) {
            OrderHistDto.OrderItemHistDto orderItemHistDto = OrderHistDto.OrderItemHistDto.builder()
                    .imageUrl("/images/2a87e656-79f0-405f-98da-aee2be5ba333.jpg")
                    .orderPrice(5000)
                    .count(1)
                    .itemName("청바지" + i)
                    .build();
            orderItemHistDtos.add(orderItemHistDto);
        }
        orderHistDto.setOrderItemHistDtos(orderItemHistDtos);


        Page<OrderHistDto> pageOrderHistDtos = new PageImpl<>(orderHistDtos, pageable, 30);
        model.addAttribute("orders", pageOrderHistDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5); // 메인페이지에 노출되는 최대 페이지 갯수
        return "orderhist/orderhist";
    }

}
