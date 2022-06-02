package com.shop.projectlion.domain.delivery.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;


    public Delivery findById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_DELIVERY));
    }

    public List<Delivery> findByMember(Member member) {
        return deliveryRepository.findByMember(member);
    }

//    public Delivery findByItem(Item item){
//        return deliveryRepository.findByItem(item);
//    }


}
