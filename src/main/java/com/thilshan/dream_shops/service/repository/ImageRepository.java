package com.thilshan.dream_shops.service.repository;

import com.thilshan.dream_shops.model.Image;
import com.thilshan.dream_shops.service.image.ImageService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long id);
}