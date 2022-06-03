package com.shop.projectlion.domain.delivery.repository;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByMember(Member member);

//    Optional<List<Delivery>> findByMember(Member member);
//    Delivery findByItem(Item item);
}
