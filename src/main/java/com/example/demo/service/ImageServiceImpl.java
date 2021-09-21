package com.example.demo.service;

import com.example.demo.dto.ImageDtoIn;
import com.example.demo.dto.ImageDtoOut;
import com.example.demo.dto.UpdateImageDtoIn;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.User;
import com.example.demo.error.NotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public ImageDtoOut getImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("Image not found"));
        return toDto(image);
    }

    @Override
    public List<ImageDtoOut> getAllImages(int page) {
        return imageRepository.findAll(PageRequest.of(page - 1, 20)).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDtoOut addImage(ImageDtoIn imageDtoIn) {
        Category category = categoryRepository.findById(imageDtoIn.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        User user = userRepository.getById(imageDtoIn.getUserId());
        Image image = new Image();
        image.setName(imageDtoIn.getName());
        image.addCategory(category);
        image.setUser(user);
        imageRepository.save(image);
        return toDto(image);
    }

    @Override
    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found"));
        imageRepository.delete(image);
    }

    @Override
    public ImageDtoOut updateImage(UpdateImageDtoIn data, Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found"));
        image.setName(data.getName());
        imageRepository.save(image);
        return toDto(image);
    }

    @Override
    public List<ImageDtoOut> getImagesByCategories(List<Long> categories, int page) {
        Set<Image> images = imageRepository.getImagesByCategories(categories);
        return images.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ImageDtoOut toDto(Image image) {
        return new ImageDtoOut(image.getId(), image.getName());
    }
}
