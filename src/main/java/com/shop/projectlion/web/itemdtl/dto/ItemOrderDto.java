package com.shop.projectlion.web.itemdtl.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemOrderDto {

    @NotNull(message = "상품 아이디는 필수 값입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    @NotNull(message = "주문 수량은 필수 입력 값입니다.")
    private Integer count;

}