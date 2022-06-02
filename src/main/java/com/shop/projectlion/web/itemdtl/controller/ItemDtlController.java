package com.shop.projectlion.web.itemdtl.controller;

import com.shop.projectlion.domain.order.service.OrderService;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import com.shop.projectlion.web.itemdtl.dto.ItemOrderDto;
import com.shop.projectlion.web.itemdtl.service.ItemDtlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/itemdtl")
public class ItemDtlController {

    private final ItemDtlService itemDtlService;
    private final OrderService orderService;

    @GetMapping(value = "/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){

        ItemDtlDto itemDtlDto = itemDtlService.getItemDtl(itemId);

        model.addAttribute("item", itemDtlDto);
        return "itemdtl/itemdtl";
    }

    @PostMapping("/order")
    public ResponseEntity<String> order(@RequestBody @Valid ItemOrderDto itemOrderDto
            , BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            LocalDateTime orderDateTime = LocalDateTime.now();
            itemDtlService.order(itemOrderDto, principal.getName(), orderDateTime);
        } catch (BusinessException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("order success");
    }

}
