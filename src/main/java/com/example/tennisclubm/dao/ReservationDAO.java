package com.example.tennisclubm.dao;

import com.example.tennisclubm.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationDAO {
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findByCourtId(Long courtId);
    List<Reservation> findByPhoneNumber(String phoneNumber);
    List<Reservation> findByUserId(Long userId);
    Reservation save(Reservation reservation);
    Reservation update(Reservation reservation);
    void delete(Long id);
    boolean existsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    List<Reservation> findBySurfaceType(Long surfaceTypeId);
}
