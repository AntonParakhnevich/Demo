package com.example.demo.service;

import com.example.demo.dto.ImageDtoIn;
import com.example.demo.dto.ImageDtoOut;
import com.example.demo.dto.UpdateImageDtoIn;

import java.util.List;

public interface ImageService {
    ImageDtoOut getImage(Long id);

    List<ImageDtoOut> getAllImages(int page);

    ImageDtoOut addImage(ImageDtoIn imageDtoIn);

    void deleteImage(Long id);

    ImageDtoOut updateImage(UpdateImageDtoIn data, Long id);

    List<ImageDtoOut> getImagesByCategories(List<Long> categoriesIds, int page);
}
