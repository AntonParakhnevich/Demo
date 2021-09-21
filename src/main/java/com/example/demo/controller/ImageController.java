package com.example.demo.controller;

import com.example.demo.dto.ImageDtoIn;
import com.example.demo.dto.ImageDtoOut;
import com.example.demo.dto.UpdateImageDtoIn;
import com.example.demo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "{id}")
    public ImageDtoOut getImage(@PathVariable Long id) {
        return imageService.getImage(id);
    }

    @GetMapping
    public List<ImageDtoOut> getImages(@RequestParam int page) {
        return imageService.getAllImages(page);
    }

    @PostMapping
    public ImageDtoOut addImage(@Valid @RequestBody ImageDtoIn imageDtoIn) {
        return imageService.addImage(imageDtoIn);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ImageDtoOut updateImage(@PathVariable Long id,
                                   @Valid @RequestBody UpdateImageDtoIn data) {
        return imageService.updateImage(data, id);

    }

    @GetMapping(value = "/images")
    public List<ImageDtoOut> getImagesByCategories(@RequestParam List<Long> categories, @RequestParam int page) {
        return imageService.getImagesByCategories(categories, page);
    }
}
