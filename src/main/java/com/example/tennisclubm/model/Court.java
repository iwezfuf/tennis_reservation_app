package com.example.tennisclubm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "surface_type_id", nullable = false)
    private SurfaceType surfaceType;

    @Setter
    @Column (name = "is_deleted", nullable = false)
    private boolean deleted = false; // for soft delete

    public Court() {
    }

    public Court(String name, SurfaceType surfaceType) {
        this.name = name;
        this.surfaceType = surfaceType;
    }

    public double getPricePerMinute() {
        return surfaceType.getPricePerMinute();
    }
}