package com.example.tennisclubm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SurfaceTypeDTOview {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Price per minute is required")
    private double pricePerMinute;
}