package com.shop.projectlion.web.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MainItemDto {

    private Long itemId;

    private String itemName;

    private String itemDetail;

    private String imageUrl;

    private Integer price;

    @Builder
    public MainItemDto(Long itemId, String itemName, String itemDetail,
                       String imageUrl, Integer price){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imageUrl = imageUrl;
        this.price = price;
    }


}
