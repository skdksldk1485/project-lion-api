package com.shop.projectlion.web.itemdtl.service;

import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import com.shop.projectlion.domain.itemImage.service.ItemImageService;
import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemDtlService {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final DeliveryService deliveryService;

    public ItemDtlDto getItemDtl(Long itemId){
        Item item = itemService.findByItemId(itemId);
//        Delivery dtlDelivery = deliveryService.findByItem(item);
        List<ItemImage> itemDtlImage = itemImageService.getItemDtlImage(item);
        return ItemDtlDto.of(item, itemDtlImage);
    }


}
