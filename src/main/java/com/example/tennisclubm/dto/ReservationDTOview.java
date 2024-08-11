package com.example.tennisclubm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTOview {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Court is required")
    private CourtDTOview court;

    @NotNull(message = "User is required")
    private UserDTOview user;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotNull(message = "Doubles is required")
    private boolean isDoubles;
}
