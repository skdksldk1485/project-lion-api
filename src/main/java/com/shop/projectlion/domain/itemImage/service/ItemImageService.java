package com.shop.projectlion.domain.itemImage.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import com.shop.projectlion.domain.itemImage.repository.ItemImageRepository;
import com.shop.projectlion.infra.FileService;
import com.shop.projectlion.infra.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final FileService fileService;
    private final String IMAGE_URL_PREFIX = "/images/";

    @Transactional
    public void saveItemImages(Item item, List<MultipartFile> itemImageFiles) throws Exception{
        for(int i = 0; i<itemImageFiles.size(); i++){
            Boolean isRepImage = i == 0;
            saveItemImage(item, itemImageFiles.get(i), isRepImage);
        }
    }

    @Transactional
    public void saveItemImage(Item item, MultipartFile itemImageFile, Boolean isRepImage) throws IOException {

        UploadFile uploadFile = fileService.storeFile(itemImageFile);
        String storeFileName = uploadFile != null ? uploadFile.getStoreFileName() : "";
        String originalFilename = uploadFile != null ? uploadFile.getOriginalFileName() : "";
        String imageUrl = uploadFile != null ? IMAGE_URL_PREFIX + storeFileName : "";

        ItemImage itemImage = ItemImage.builder()
                .imageName(storeFileName)
                .imageUrl(imageUrl)
                .originalImageName(originalFilename)
                .isRepImage(isRepImage)
                .build();

        ItemImage saveItemImage = ItemImage.createItemImage(itemImage, item);
        itemImageRepository.save(saveItemImage);
    }

    public List<ItemImage> findByItemOrderByIdAsc(Item item) {
        return itemImageRepository.findByItemOrderByIdAsc(item);
    }

    @Transactional
    public void updateItemImage(ItemImage itemImage, MultipartFile itemImageFile) throws IOException {
        // 기존 상품 이미지 파일이 존재하는 경우 파일 삭제
        if(StringUtils.hasText(itemImage.getImageName())) {
            fileService.deleteFile(itemImage.getImageUrl());
        }

        // 새로운 이미지 파일 등록
        UploadFile uploadFile = fileService.storeFile(itemImageFile);
        String originalFilename = uploadFile.getOriginalFileName();
        String storeFileName = uploadFile.getStoreFileName();
        String imageUrl = IMAGE_URL_PREFIX + storeFileName;

        // 상품 이미지 파일 정보 업데이트
        itemImage.updateItemImage(originalFilename, storeFileName, imageUrl);
    }

    @Transactional
    public void deleteItemImage(ItemImage itemImage) throws IOException {
        // 기존 이미지 파일 삭제
        String fileUploadUrl = fileService.getFullFileUploadPath(itemImage.getImageName());
        fileService.deleteFile(fileUploadUrl);
        // 이미지 정보 초기화
        itemImage.initImageInfo();
    }

    public ItemImage getRepImage(Long itemId){
        return itemImageRepository.findByItemIdAndIsRepImage(itemId, true);
    }




}
