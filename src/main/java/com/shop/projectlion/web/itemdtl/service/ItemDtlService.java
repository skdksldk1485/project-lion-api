package com.shop.projectlion.web.itemdtl.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import com.shop.projectlion.domain.itemImage.service.ItemImageService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.domain.order.entity.Order;
import com.shop.projectlion.domain.order.service.OrderService;
import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import com.shop.projectlion.web.itemdtl.dto.ItemOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemDtlService {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final MemberService memberService;
    private final OrderService orderService;

    public ItemDtlDto getItemDtl(Long itemId){
        Item item = itemService.findByItemId(itemId);
        List<ItemImage> itemDtlImage = itemImageService.getItemDtlImage(item);
        return ItemDtlDto.of(item, itemDtlImage);
    }

    @Transactional
    public void order(ItemOrderDto itemOrderDto, String email, LocalDateTime orderTime) {

        // 1. 상품 조회
        Item item = itemService.findByItemId(itemOrderDto.getItemId());

        // 2. 회원 조회
        Member member = memberService.findMemberByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_MEMBER));

        // 3. 주문 생성
        Order order = Order.createOrder(orderTime, member);

        // 4. 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, itemOrderDto.getCount(), item.getPrice());
        order.addOrderItem(orderItem);

        // 5. 주문 저장
        orderService.order(order);
    }


}
