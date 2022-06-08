package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeliveryDto {

    private Long deliveryId;
    private String deliveryName;
    private int deliveryFee;

    @Builder
    public DeliveryDto(Long deliveryId, String deliveryName, int deliveryFee){
        this.deliveryFee = deliveryFee;
        this.deliveryId = deliveryId;
        this.deliveryName = deliveryName;
    }

    public static DeliveryDto toDto(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryFee(delivery.getDeliveryFee())
                .deliveryId(delivery.getId())
                .deliveryName(delivery.getDeliveryName())
                .build();

    }

}
