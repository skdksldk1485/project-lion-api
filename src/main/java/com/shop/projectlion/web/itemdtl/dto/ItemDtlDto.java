package com.shop.projectlion.web.itemdtl.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static ItemDtlDto of(Item item, List<ItemImage> itemImages) {
        List<ItemImageDto> itemImageDtos = ItemImageDto.of(itemImages);
        ItemDtlDto itemDtlDto = ItemDtlDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .itemDetail(item.getItemDetail())
                .stockNumber(item.getStockNumber())
                .itemSellStatus(item.getItemSellStatus())
                .deliveryFee(item.getDelivery().getDeliveryFee())
                .itemImageDtos(itemImageDtos)
                .build();
        return itemDtlDto;
    }

    @Getter @Setter
    @Builder @AllArgsConstructor
    public static class ItemImageDto {
        private String imageUrl;

        public static List<ItemImageDto> of(List<ItemImage> itemImages) {
            return itemImages.stream().map(itemImage -> ItemImageDto.builder()
                    .imageUrl(itemImage.getImageUrl())
                    .build()).collect(Collectors.toList());
        }
    }
}
