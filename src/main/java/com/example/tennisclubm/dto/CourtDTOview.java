package com.example.tennisclubm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CourtDTOview {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Surface type is required")
    private SurfaceTypeDTOview surfaceType;
}