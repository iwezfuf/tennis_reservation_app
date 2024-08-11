package com.example.tennisclubm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Entity
public class Reservation {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    @Setter
    private Court court;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private User user;

    @Column(nullable = false)
    @Setter
    private LocalDateTime startTime;

    @Column(nullable = false)
    @Setter
    private LocalDateTime endTime;

    @Column(nullable = false)
    @Setter
    private boolean isDoubles; // true for doubles, false for singles

    @Column(nullable = false)
    private double price;

    @Setter
    @Column (name = "is_deleted", nullable = false)
    private boolean deleted = false; // for soft delete

    public Reservation() {
    }

    public Reservation(Court court, User user, LocalDateTime startTime, LocalDateTime endTime, boolean isDoubles, double price) {
        this.court = court;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDoubles = isDoubles;
        this.price = price;
    }

    public double calculatePrice() {
        long minutes = startTime.until(endTime, ChronoUnit.MINUTES);
        return minutes * court.getSurfaceType().getPricePerMinute();
    }
}
