package com.shop.projectlion.web.itemdtl.controller;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/itemdtl")
public class ItemDtlController {

    @GetMapping(value = "/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemDtlDto itemDtlDto = ItemDtlDto.builder()
                .itemId(1L)
                .itemName("청바지")
                .itemDetail("파란색 청바지")
                .itemSellStatus(ItemSellStatus.SELL)
                .price(25000)
                .deliveryFee(3000)
                .stockNumber(500)
                .build();

        List<ItemDtlDto.ItemImageDto> itemImageDtos = new ArrayList<>();
        ItemDtlDto.ItemImageDto itemImageDto = new ItemDtlDto.ItemImageDto("/images/2a87e656-79f0-405f-98da-aee2be5ba333.jpg");
        itemImageDtos.add(itemImageDto);
        itemDtlDto.setItemImageDtos(itemImageDtos);

        model.addAttribute("item", itemDtlDto);
        return "itemdtl/itemdtl";
    }
}
