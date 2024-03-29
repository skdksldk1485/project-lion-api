package com.shop.projectlion.domain.item.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.error.exception.OutofStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "item")
public class Item extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private Long id;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Column(nullable = false)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ItemSellStatus itemSellStatus;

    @Column(nullable = false, length = 20)
    private Integer price;

    @Column(nullable = false)
    private Integer stockNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Item(String itemDetail,
                String itemName,
                ItemSellStatus itemSellStatus,
                Integer price,
                Integer stockNumber,
                Delivery delivery,
                Member member){
        this.itemDetail = itemDetail;
        this.itemName = itemName;
        this.itemSellStatus = itemSellStatus;
        this.price = price;
        this.stockNumber = stockNumber;
        this.delivery = delivery;
        this.member = member;
    }

    public static Item createItem(Item item, Member member, Delivery delivery){
        return Item.builder()
                .itemDetail(item.getItemDetail())
                .itemName(item.getItemName())
                .itemSellStatus(item.getItemSellStatus())
                .price(item.getPrice())
                .stockNumber(item.getStockNumber())
                .delivery(delivery)
                .member(member)
                .build();
    }

    public void updateItem(Item updateItem) {
        this.itemName = updateItem.getItemName();
        this.price = updateItem.getPrice();
        this.stockNumber = updateItem.getStockNumber();
        this.itemDetail = updateItem.getItemDetail();
        this.itemSellStatus = updateItem.getItemSellStatus();
    }

    public void updateDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void decreaseStock(Integer stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0) {
            throw new OutofStockException(ErrorCode.NOT_EXISTS_STOCK.getMessage() + " (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
        if(this.stockNumber == 0) {
            this.itemSellStatus = ItemSellStatus.SOLD_OUT;
        }
    }

    public void increaseStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}
