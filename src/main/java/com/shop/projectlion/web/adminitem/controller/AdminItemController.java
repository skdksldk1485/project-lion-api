package com.shop.projectlion.web.adminitem.controller;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/items")
public class AdminItemController {

    @GetMapping("/new")
    public String itemForm(Model model, Principal principal) {

        List<DeliveryDto> deliveryDtos = new ArrayList<>();
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryId(1L)
                .deliveryName("마포구 물류센터")
                .deliveryFee(3000)
                .build();
        deliveryDtos.add(deliveryDto);

        model.addAttribute("deliveryDtos", deliveryDtos);
        model.addAttribute("insertItemDto", new InsertItemDto());

        return "adminitem/registeritemform";
    }

    @GetMapping("/{itemId}")
    public String itemEdit(@PathVariable Long itemId, Model model, Principal principal) {

        // 배송 정보
        List<DeliveryDto> deliveryDtos = new ArrayList<>();
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryId(1L)
                .deliveryName("마포구 물류센터")
                .deliveryFee(3000)
                .build();
        deliveryDtos.add(deliveryDto);

        // 상품 이미지
        List<UpdateItemDto.ItemImageDto> itemImageDtos = new ArrayList<>();
        for(int i=1;i<6;i++) {
            UpdateItemDto.ItemImageDto itemImageDto = UpdateItemDto.ItemImageDto
                    .builder()
                    .itemImageId(Long.valueOf(i+4))
                    .originalImageName("파란색 청바지 이미지" + i)
                    .build();
            itemImageDtos.add(itemImageDto);
        }

        // 상품 정보
        UpdateItemDto updateItemDto = UpdateItemDto.builder()
                .itemId(2L)
                .itemName("청바지")
                .itemDetail("파란색 청바지")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockNumber(150)
                .price(30000)
                .deliveryId(1L)
                .itemImageDtos(itemImageDtos)
                .build();

        model.addAttribute("deliveryDtos", deliveryDtos);
        model.addAttribute("updateItemDto", updateItemDto);

        return "adminitem/updateitemform";
    }

}
