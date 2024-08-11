package com.example.tennisclubm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CourtDTOcreate {
    @NotNull(message = "Name is required")
    @Schema(description = "Court name", example = "Court 1")
    private String name;

    @Schema(description = "Surface type ID", example = "1")
    @NotNull(message = "Surface type ID is required")
    private Long surfaceTypeId;
}