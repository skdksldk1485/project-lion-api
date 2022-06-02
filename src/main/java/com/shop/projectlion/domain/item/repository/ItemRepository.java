package com.shop.projectlion.domain.item.repository;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select t " +
            "from Item t " +
            "where (t.itemName like %:searchQuery% or t.itemDetail like %:searchQuery%) and t.itemSellStatus = :status ")
    Page<Item> getMainItemsPage(String searchQuery, ItemSellStatus status, Pageable pageable);

}
