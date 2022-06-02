package com.shop.projectlion.web.itemdtl.controller;

import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import com.shop.projectlion.web.itemdtl.service.ItemDtlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/itemdtl")
public class ItemDtlController {

    private final ItemDtlService itemDtlService;

    @GetMapping(value = "/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){

        ItemDtlDto itemDtlDto = itemDtlService.getItemDtl(itemId);

        model.addAttribute("item", itemDtlDto);
        return "itemdtl/itemdtl";
    }

}
