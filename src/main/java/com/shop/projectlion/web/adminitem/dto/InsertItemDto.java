package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class InsertItemDto {

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private Long deliveryId;

    private List<MultipartFile> itemImageFiles;

}
