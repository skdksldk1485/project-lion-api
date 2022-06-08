package com.shop.projectlion.web.main.controller;

import com.shop.projectlion.web.main.dto.ItemSearchDto;
import com.shop.projectlion.web.main.dto.MainItemDto;
import com.shop.projectlion.web.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
        Page<MainItemDto> pageMainItemDto = mainService.getMainItems(itemSearchDto, page);

        model.addAttribute("items", pageMainItemDto);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5); // 메인페이지에 노출되는 최대 페이지 갯수

        return "main/mainpage";
    }

}