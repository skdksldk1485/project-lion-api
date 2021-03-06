package com.shop.projectlion.web.adminitem.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import com.shop.projectlion.domain.itemImage.service.ItemImageService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        Delivery delivery = deliveryService.findById(insertItemDto.getDeliveryId());
        Item item = insertItemDto.toEntity();
        Item saveItem = Item.createItem(item, member, delivery);
        saveItem = itemService.createItem(saveItem);

        itemImageService.saveItemImages(saveItem, insertItemDto.getItemImageFiles());

        return saveItem;
    }

    public UpdateItemDto getUpdateItemDto(Long itemId) {
        Item item = itemService.findByItemId(itemId);
        List<ItemImage> itemImages = itemImageService.findByItemOrderByIdAsc(item);
        return UpdateItemDto.of(item, itemImages);
    }

    public List<UpdateItemDto.ItemImageDto> getItemImageDtos(Long itemId) {
        Item item = itemService.findByItemId(itemId);
        List<ItemImage> itemImages = itemImageService.findByItemOrderByIdAsc(item);
        return UpdateItemDto.ItemImageDto.of(itemImages);
    }

    @Transactional
    public void updateItem(UpdateItemDto updateItemDto) throws IOException {

        // ?????? ????????????
        Item item = updateItemInfo(updateItemDto);

        // ?????? ????????????
        Delivery delivery = deliveryService.findById(updateItemDto.getDeliveryId());
        item.updateDelivery(delivery);

        // ?????? ????????? ????????????
        updateItemImages(updateItemDto, item);

    }

    private Item updateItemInfo(UpdateItemDto updateItemDto) {
        Item updateItem = updateItemDto.toEntity();
        Item updatedItem = itemService.updateItem(updateItemDto.getItemId(), updateItem);
        return updatedItem;
    }

    private void updateItemImages(UpdateItemDto updateItemDto, Item item) throws IOException {

        // ????????????????????? ????????? ?????? ????????? ??????
        List<ItemImage> itemImages = itemImageService.findByItemOrderByIdAsc(item);
        List<String> originalImageNames = updateItemDto.getOriginalImageNames(); // ?????? ?????? ?????? ?????? ?????? ?????? ?????? ???????????? ??????
        List<MultipartFile> itemImageFiles = updateItemDto.getItemImageFiles(); // ?????? ?????? ????????? ??????

        for(int i=0;i<itemImages.size();i++) {
            ItemImage itemImage = itemImages.get(i);
            String originalImageName = originalImageNames.get(i);
            MultipartFile itemImageFile = itemImageFiles.get(i);
            if(!itemImageFile.isEmpty()) {  // ?????? ?????? ?????? or ?????? ?????? ?????? ??????
                itemImageService.updateItemImage(itemImage, itemImageFile);
            } else if(!StringUtils.hasText(originalImageName) &&
                    StringUtils.hasText(itemImage.getOriginalImageName())) { // ?????? ?????? ??????
                itemImageService.deleteItemImage(itemImage);
            }
        }
    }




}
