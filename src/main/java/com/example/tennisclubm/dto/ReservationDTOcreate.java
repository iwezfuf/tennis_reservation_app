package com.example.tennisclubm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTOcreate {
    @Schema(description = "Court ID", example = "1")
    @NotNull(message = "Court ID is required")
    private Long courtId;

    @Schema(description = "Phone number", example = "123")
    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @Schema(description = "Start time of the reservation", example = "2024-06-15T10:00:00.000Z")
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @Schema(description = "End time of the reservation", example = "2024-06-15T12:00:00.000Z")
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @Schema(description = "Is doubles game", example = "true")
    @NotNull(message = "Doubles is required")
    private boolean isDoubles;
}
