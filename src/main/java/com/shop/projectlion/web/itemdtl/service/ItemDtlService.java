package com.shop.projectlion.web.itemdtl.service;

import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemImage.service.ItemImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemDtlService {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final DeliveryService deliveryService;

//    public ItemDtlDto getItemDtl(Long itemId){
//
//    }
}
