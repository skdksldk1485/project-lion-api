package com.shop.projectlion.web.adminitem.controller;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.global.config.security.UserDetailsImpl;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import com.shop.projectlion.web.adminitem.service.AdminItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/items")
public class AdminItemController {

    private final AdminItemService adminItemService;

    @ModelAttribute("deliveryDtos")
    public List<DeliveryDto> deliveryDtos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        List<DeliveryDto> deliveryDtos = adminItemService.getMemberDeliveryDtos(email);
        return deliveryDtos;
    }

    @GetMapping("/new")
    public String itemForm(Model model) {
        model.addAttribute("insertItemDto", new InsertItemDto());
        return "adminitem/registeritemform";
    }


    @PostMapping("/new")
    public String createItem(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute("insertItemDto") @Validated InsertItemDto insertItemDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            return "adminitem/registeritemform";
        } else if (insertItemDto.getItemImageFiles().get(0).isEmpty()) {
            bindingResult.reject("notExistFirstImage", ErrorCode.NOT_EXISTS_FIRST_ITEM_IMAGE.getMessage());
            return "adminitem/registeritemform";
        }
        try {
            Item savedItem = adminItemService.createItem(insertItemDto, userDetails.getUsername());
            redirectAttributes.addAttribute("itemId", savedItem.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            bindingResult.reject("globalError", "상품 등록 중 에러가 발생했습니다.");
            return "adminitem/registeritemform";
        }

        return "redirect:/admin/items/{itemId}";
    }

    @GetMapping("/{itemId}")
    public String itemEdit(@PathVariable Long itemId, Model model, Principal principal) {
        UpdateItemDto updateItemDto = adminItemService.getUpdateItemDto(itemId);
        model.addAttribute("updateItemDto", updateItemDto);
        return "adminitem/updateitemform";
    }

}
