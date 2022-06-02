package com.shop.projectlion.web.main.controller;

import com.shop.projectlion.web.main.dto.ItemSearchDto;
import com.shop.projectlion.web.main.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6); // 페이지 및 한 페이지에 나올 페이지 갯수로 pageable 객체 생성

        List<MainItemDto> mainItemDtos = new ArrayList<>();
        for(int i=0;i<6;i++) {
            MainItemDto mainItemDto = MainItemDto.builder()
                    .itemId(Long.valueOf(i))
                    .itemName("셔츠" + i)
                    .itemDetail("상품 상세" +i)
                    .price(3000*(i+1))
                    .imageUrl("/images/2a87e656-79f0-405f-98da-aee2be5ba333.jpg")
                    .build();
            mainItemDtos.add(mainItemDto);
        }
        Page<MainItemDto> pageMainItemDto = new PageImpl<>(mainItemDtos, pageable, 30);

        model.addAttribute("items", pageMainItemDto);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5); // 메인페이지에 노출되는 최대 페이지 갯수

        return "main/mainpage";
    }

}