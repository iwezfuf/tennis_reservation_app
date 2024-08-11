package com.example.tennisclubm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SurfaceTypeDTOcreate {
    @NotNull(message = "Name is required")
    @Schema(description = "Surface type name", example = "Grass")
    private String name;

    @Schema(description = "Price per minute", example = "0.5")
    @NotNull(message = "Price per minute is required")
    private double pricePerMinute;
}