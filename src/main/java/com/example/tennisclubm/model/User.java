package com.example.tennisclubm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Setter
    @Column (name = "is_deleted", nullable = false)
    private boolean deleted = false; // for soft delete

    public User() {
    }

    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
