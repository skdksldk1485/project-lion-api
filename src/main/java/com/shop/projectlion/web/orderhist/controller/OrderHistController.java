package com.shop.projectlion.web.orderhist.controller;

import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import com.shop.projectlion.web.orderhist.service.OrderHistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderhist")
public class OrderHistController {

    private final OrderHistService orderHistService;

    @GetMapping
    public String orderHist(Optional<Integer> page, Model model, Principal principal) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<OrderHistDto> pageOrderHistDtos = orderHistService.getOrderHistDtos(principal.getName(), pageable);
        model.addAttribute("orders", pageOrderHistDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5); // 메인페이지에 노출되는 최대 페이지 갯수
        return "orderhist/orderhist";
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {

        try {
            orderHistService.cancelOrder(orderId, principal.getName());
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok("order cancel success");
    }

}
