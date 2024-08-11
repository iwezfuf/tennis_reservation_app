package com.example.tennisclubm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTOcreate {
    @NotNull(message = "Name is required")
    @Schema(description = "User name", example = "Hal Incandenza")
    private String name;

    @Schema(description = "Phone number", example = "2267709")
    @NotNull(message = "Phone number per minute is required")
    private String phoneNumber;
}