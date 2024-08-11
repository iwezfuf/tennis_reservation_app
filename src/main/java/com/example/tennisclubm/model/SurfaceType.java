package com.example.tennisclubm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SurfaceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double pricePerMinute;

    @Setter
    @Column (name = "is_deleted", nullable = false)
    private boolean deleted = false; // for soft delete

    public SurfaceType() {
    }

    public SurfaceType(String name, double pricePerMinute) {
        this.name = name;
        this.pricePerMinute = pricePerMinute;
    }
}
