package com.thilshan.dream_shops.service.image;

import com.thilshan.dream_shops.dto.ImageDto;
import com.thilshan.dream_shops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId, Long productId);
}
