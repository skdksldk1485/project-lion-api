package com.shop.projectlion.web.main.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemImage.service.ItemImageService;
import com.shop.projectlion.web.main.dto.ItemSearchDto;
import com.shop.projectlion.web.main.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

    private final ItemService itemService;
    private final ItemImageService itemImageService;

    public Page<MainItemDto> getMainItems(ItemSearchDto itemSearchDto, Optional<Integer> page){

        String searchQuery = itemSearchDto.getSearchQuery();
        Pageable pageable = PageRequest.of(page.orElse(0),6);
        Page<Item> mainPageItems = itemService.getMainItems(searchQuery,pageable);
        return toDto(mainPageItems);
    }

    private Page<MainItemDto> toDto(Page<Item> mainPageItems){

        return mainPageItems.map(item -> MainItemDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .itemDetail(item.getItemDetail())
                .imageUrl(itemImageService.getRepImage(item.getId()).getImageUrl())
                .price(item.getPrice())
                .build());
    }


}
