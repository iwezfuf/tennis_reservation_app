package com.example.tennisclubm.service;

import com.example.tennisclubm.dao.CourtDAO;
import com.example.tennisclubm.model.Court;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtService {

    @Autowired
    private CourtDAO courtDAO;

    @Autowired
    private ReservationService reservationService;

    public Court getCourtById(Long id) {
        return courtDAO.findById(id);
    }

    public List<Court> getAllCourts() {
        return courtDAO.findAll();
    }

    public Court createCourt(Court court) {
        return courtDAO.save(court);
    }

    public Court updateCourt(Court court) {
        return courtDAO.update(court);
    }

    public void deleteCourt(Long id) {
        courtDAO.delete(id);
        // Delete all reservations for the court
        reservationService.deleteReservationsByCourt(id);
    }

    public void deleteCourtsBySurfaceType(Long surfaceTypeId) {
        List<Court> courts = courtDAO.findBySurfaceType(surfaceTypeId);
        courts.forEach(court -> deleteCourt(court.getId()));
    }
}

