package com.shop.projectlion.web.itemdtl.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ItemDtlDto {

    private Long itemId;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private Integer deliveryFee;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    @Getter @Setter
    @Builder @AllArgsConstructor
    public static class ItemImageDto {
        private String imageUrl;
    }
}
