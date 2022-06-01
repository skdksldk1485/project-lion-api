package com.shop.projectlion.domain.itemImage.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.item.entity.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "item_image")
public class ItemImage extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_image_id")
    private Long id;

    @Column(unique = true, nullable = false, length = 500)
    private String imageName;

    @Column(nullable = false, length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean isRepImage;

    @Column(nullable = false, length = 200)
    private String originalImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Builder
    public ItemImage(String imageName, String imageUrl, String originalImageName, Boolean isRepImage, Item item) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.isRepImage = isRepImage;
        this.originalImageName = originalImageName;
        this.item = item;
    }

    public static ItemImage createItemImage(ItemImage itemImage, Item item) {
        return ItemImage.builder()
                .imageName(itemImage.getImageName())
                .imageUrl(itemImage.getImageUrl())
                .originalImageName(itemImage.getOriginalImageName())
                .isRepImage(itemImage.getIsRepImage())
                .item(item)
                .build();
    }

    public static ItemImage updateItemImage(ItemImage itemImage) {
        return ItemImage.builder()
                .imageName(itemImage.getImageName())
                .imageUrl(itemImage.getImageUrl())
                .originalImageName(itemImage.getOriginalImageName())
                .build();
    }


}
