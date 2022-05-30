package com.shop.projectlion.domain.itemImage.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.item.entity.Item;
import lombok.AccessLevel;
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

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String isRepImage;

    @Column(nullable = false)
    private String originalImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;



}
