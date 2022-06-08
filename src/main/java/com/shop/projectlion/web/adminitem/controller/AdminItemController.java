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
import org.springframework.util.StringUtils;
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

    @PostMapping("/{itemId}")
    public String updateItem(@PathVariable Long itemId, @Validated UpdateItemDto updateItemDto, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes) {

        if(updateItemDto.getItemImageFiles().get(0).isEmpty() &&
                !StringUtils.hasText(updateItemDto.getOriginalImageNames().get(0))){ // 첫번째 상품 이미지 필수 체크
            bindingResult.reject("firstItemImageNotExists", ErrorCode.NOT_EXISTS_FIRST_ITEM_IMAGE.getMessage());
            List<UpdateItemDto.ItemImageDto> itemImageDtos = adminItemService.getItemImageDtos(itemId);
            updateItemDto.setItemImageDtos(itemImageDtos);
            return "adminitem/updateitemform";
        } else if(bindingResult.hasErrors()) {
            List<UpdateItemDto.ItemImageDto> itemImageDtos = adminItemService.getItemImageDtos(itemId);
            updateItemDto.setItemImageDtos(itemImageDtos);
            return "adminitem/updateitemform";
        }

        try {
            adminItemService.updateItem(updateItemDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            bindingResult.reject("globalError", "상품 수정 중 에러가 발생하였습니다.");
            return "adminitem/updateitemform";
        }

        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/admin/items/{itemId}";
    }

}
