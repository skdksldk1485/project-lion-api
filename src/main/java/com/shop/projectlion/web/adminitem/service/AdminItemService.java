package com.shop.projectlion.web.adminitem.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemImage.service.ItemImageService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminItemService {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final MemberService memberService;
    private final DeliveryService deliveryService;


    public List<DeliveryDto> getMemberDeliveryDtos(String email){
        Member member = memberService.findMemberByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_MEMBER));
        List<Delivery> deliveries = deliveryService.findByMember(member);
        List<DeliveryDto> deliveryDtos = new ArrayList<>();
        for (int i = 0; i < deliveries.size(); i++) {
            Delivery eachDelivery = deliveries.get(i);
            DeliveryDto deliveryDto = DeliveryDto.toDto(eachDelivery);
            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;
    }


    @Transactional
    public Item createItem(InsertItemDto insertItemDto, String email) throws Exception{
        Member member = memberService.findMemberByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_MEMBER));
        Delivery delivery = deliveryService.findById(insertItemDto.getDeliveryId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_DELIVERY));
        Item item = insertItemDto.toEntity();
        Item saveItem = Item.createItem(item, member, delivery);
        saveItem = itemService.createItem(saveItem);

        itemImageService.saveItemImages(saveItem, insertItemDto.getItemImageFiles());

        return saveItem;
    }


}
