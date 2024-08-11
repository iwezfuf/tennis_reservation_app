package com.example.tennisclubm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTOview {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Phone number is required")
    private String phoneNumber;
}