package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDtoIn {
    @NotBlank
    private String name;
    @NotNull
    @Min(1)
    private Long categoryId;
    @NotNull
    @Min(1)
    private Long userId;
}
