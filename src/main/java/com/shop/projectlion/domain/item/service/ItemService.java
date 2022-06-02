package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item createItem(Item item){
        itemRepository.save(item);
        return item;
    }

    public Item findByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_ITEM));
    }

    @Transactional
    public Item updateItem(Long itemId, Item updateItem) {
        Item savedItem = findByItemId(itemId);
        savedItem.updateItem(updateItem);
        return savedItem;
    }

    public Page<Item> getMainItems(String searchQurey, Pageable pageable){
        return itemRepository.getMainItemsPage(searchQurey, ItemSellStatus.SELL, pageable);
    }
}
