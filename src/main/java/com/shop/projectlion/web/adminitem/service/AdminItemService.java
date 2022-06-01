package com.shop.projectlion.web.adminitem.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemImage.service.ItemImageService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminItemService {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final MemberService memberService;
    private final DeliveryService deliveryService;


    @Transactional
    public Long createItem(InsertItemDto insertItemDto, String email) throws Exception{
        Member member = memberService.findMemberByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOR_FOUND_MEMBER));
        Delivery delivery = deliveryService.findById(insertItemDto.getDeliveryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_DELIVERY));
        Item item = insertItemDto.toEntity(member, delivery);
        Item createItem = Item.createItem(item, member, delivery);
        Item savedItem = itemService.createItem(createItem);

        saveItemImages(insertItemDto, savedItem);

        return savedItem.getId();
    }

    private void saveItemImages(InsertItemDto insertItemDto, Item savedItem) throws Exception {
        List<MultipartFile> multipartFiles = insertItemDto.getItemImageFiles();
        itemImageService.saveItemImage(multipartFiles, savedItem);
    }

}
