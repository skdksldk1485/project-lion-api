package com.shop.projectlion.web.adminitem.controller;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
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
import java.util.ArrayList;
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
