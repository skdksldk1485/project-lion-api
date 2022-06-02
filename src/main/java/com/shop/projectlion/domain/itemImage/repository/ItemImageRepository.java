package com.shop.projectlion.domain.itemImage.repository;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    List<ItemImage> findByItemOrderByIdAsc(Item item);

    ItemImage findByItemIdAndIsRepImage(Long itemId, Boolean isRepImage);

}
