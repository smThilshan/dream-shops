package com.thilshan.dream_shops.service.image;

import com.thilshan.dream_shops.dto.ImageDto;
import com.thilshan.dream_shops.model.Image;
import com.thilshan.dream_shops.model.Product;
import com.thilshan.dream_shops.service.exception.ResourceNotFoundException;
import com.thilshan.dream_shops.service.product.IProductService;
import com.thilshan.dream_shops.service.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No images belongs to this product"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {;
            throw new ResourceNotFoundException("No images belongs to this product");
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files){
            try {
//                Image image = new Image();
//                image.setFileName(file.getOriginalFilename());
//                image.setFileType(file.getContentType());
//                image.setImage(new SerialBlob(file.getBytes()));
//                image.setProduct(product);
//
//                String buildingDownloadUrl ="/api/v1/images/image/download";
//                String downloadUrl = buildingDownloadUrl + image.getId();
//                image.setDownloadUrl(downloadUrl);
//                Image savedImage = imageRepository.save(image);
//                savedImage.setDownloadUrl(buildingDownloadUrl + savedImage.getId());
//                imageRepository.save(savedImage);
//
//                ImageDto imageDto = new ImageDto();
//                imageDto.setImageId(savedImage.getId());
//                imageDto.setImageName(savedImage.getFileName());
//                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
//                savedImageDto.add(imageDto);
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

// 1️⃣ Save first to generate ID
                Image savedImage = imageRepository.save(image);

// 2️⃣ Build download URL using the generated ID
                String buildDownloadUrl = "/api/v1/images/image/download/";
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

// 3️⃣ Save again to update URL
                savedImage = imageRepository.save(savedImage);

// 4️⃣ Map to DTO
                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);


            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId, Long productId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
