package com.shop.projectlion.domain.itemImage.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import com.shop.projectlion.domain.itemImage.repository.ItemImageRepository;
import com.shop.projectlion.infra.FileService;
import com.shop.projectlion.infra.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final FileService fileService;

    @Transactional
    public void saveItemImage(List<MultipartFile> multipartFiles, Item savedItem) throws Exception{
        List<UploadFile> uploadFiles = fileService.storeFiles(multipartFiles);
        for(int i = 0; i<uploadFiles.size(); i++){
            String imageName = uploadFiles.get(i).getStoreFileName();
            String imageUrl = uploadFiles.get(i).getFileUploadUrl();
            String oriImageName = uploadFiles.get(i).getOriginalFileName();

            Boolean isRepImage = i == 0;

            ItemImage itemImage = ItemImage.builder()
                            .imageName(imageName)
                            .imageUrl(imageUrl)
                            .originalImageName(oriImageName)
                            .isRepImage(isRepImage)
                            .item(savedItem)
                            .build();
            itemImageRepository.save(itemImage);
        }


    }


}
