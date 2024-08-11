package com.example.tennisclubm.service;

import com.example.tennisclubm.dao.ReservationDAO;
import com.example.tennisclubm.exceptions.OverlappingReservationException;
import com.example.tennisclubm.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationDAO reservationDAO;

    public Reservation createReservation(Reservation reservation) {
        if (reservationDAO.existsByTimeRange(reservation.getStartTime(), reservation.getEndTime())) {
            throw new OverlappingReservationException("Reservation time overlaps with an existing reservation.");
        }
        // The reservation cannot span multiple days
        if (!reservation.getStartTime().toLocalDate().equals(reservation.getEndTime().toLocalDate())) {
            throw new IllegalArgumentException("Reservation cannot span multiple days.");
        }
        return reservationDAO.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationDAO.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationDAO.delete(id);
    }

    public List<Reservation> getReservationsByCourt(Long courtId) {
        return reservationDAO.findByCourtId(courtId);
    }

    public List<Reservation> getReservationsByPhone(String phoneNumber) {
        return reservationDAO.findByPhoneNumber(phoneNumber);
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationDAO.update(reservation);
    }

    public void deleteReservationsByCourt(Long courtId) {
        List<Reservation> reservations = reservationDAO.findByCourtId(courtId);
        reservations.forEach(reservation -> reservationDAO.delete(reservation.getId()));
    }

    public void deleteReservationsByUser(Long userId) {
        List<Reservation> reservations = reservationDAO.findByUserId(userId);
        reservations.forEach(reservation -> reservationDAO.delete(reservation.getId()));
    }
}

