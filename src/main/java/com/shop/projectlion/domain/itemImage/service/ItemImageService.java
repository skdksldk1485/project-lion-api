package com.shop.projectlion.domain.itemImage.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemImage.entity.ItemImage;
import com.shop.projectlion.domain.itemImage.repository.ItemImageRepository;
import com.shop.projectlion.infra.FileService;
import com.shop.projectlion.infra.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final FileService fileService;
    private final String IMAGE_URL_PREFIX = "/images/";

    //
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;


    @Transactional
    public void saveItemImages(Item item, List<MultipartFile> itemImageFiles) throws Exception{
        for(int i = 0; i<itemImageFiles.size(); i++){
            Boolean isRepImage = i == 0;
            saveItemImage(item, itemImageFiles.get(i), isRepImage);
        }
    }

    @Transactional
    public void saveItemImage(Item item, MultipartFile itemImageFile, Boolean isRepImage) throws IOException {

        List<String> fileNameList = new ArrayList<>();

        File uploadFile = convert(itemImageFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        String dirName = "static";
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(itemImageFile.getSize());
        objectMetadata.setContentType(itemImageFile.getContentType());

        try(InputStream inputStream = itemImageFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
        //로컬 파일 삭제
        removeNewFile(uploadFile);

        ItemImage itemImage = ItemImage.builder()
                .imageName(fileName)
                .imageUrl(uploadImageUrl)
                .originalImageName(itemImageFile.getOriginalFilename())
                .isRepImage(isRepImage)
                .build();

        ItemImage saveItemImage = ItemImage.createItemImage(itemImage, item);
        itemImageRepository.save(saveItemImage);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {

            return;
        }

    }

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile, dirName);
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        //File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
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

    public List<ItemImage> getItemDtlImage(Item item){
        return itemImageRepository.findByItem(item);
    }

    public ItemImage findByItemAndIsRepImage(Item item, boolean isRepImage) {
        return itemImageRepository.findByItemAndIsRepImage(item, isRepImage);
    }




}