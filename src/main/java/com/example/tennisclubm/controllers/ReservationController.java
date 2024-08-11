package com.example.tennisclubm.controllers;

import com.example.tennisclubm.dto.ReservationDTOcreate;
import com.example.tennisclubm.dto.ReservationDTOview;
import com.example.tennisclubm.exceptions.ObjectNotFound;
import com.example.tennisclubm.exceptions.OverlappingReservationException;
import com.example.tennisclubm.mappers.ReservationMapper;
import com.example.tennisclubm.model.Reservation;
import com.example.tennisclubm.model.User;
import com.example.tennisclubm.service.ReservationService;
import com.example.tennisclubm.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@OpenAPIDefinition(
        info = @Info(
                title = "Tennis Courts Reservations API",
                version = "1.0",
                description = """
                        This API allows to manage tennis courts reservations.
                        You can create, read, update and delete reservations.
                        """
        )
)
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTOview> createReservation(@Valid @RequestBody ReservationDTOcreate reservationDTOcreate) {
        try {
            String phoneNumber = reservationDTOcreate.getPhoneNumber();
            User user = userService.findByPhoneNumber(phoneNumber);
            if (user == null) {
                user = new User();
                user.setPhoneNumber(phoneNumber);
                user.setName(phoneNumber);
                user = userService.createUser(user);
            }
            Reservation reservation = reservationMapper.toEntity(reservationDTOcreate);
            reservation.setUser(user);
            Reservation createdReservation = reservationService.createReservation(reservation);
            return new ResponseEntity<>(reservationMapper.toDTO(createdReservation), HttpStatus.CREATED);
        } catch (OverlappingReservationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (ObjectNotFound | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReservationDTOview>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<ReservationDTOview> reservationDTOs = reservations.stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTOview> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(reservationMapper::toDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ObjectNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTOview> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationDTOcreate reservationDTOcreate) {
        try {
            Reservation reservation = reservationMapper.toEntity(reservationDTOcreate);
            reservation.setId(id);
            Reservation updatedReservation = reservationService.updateReservation(reservation);
            return new ResponseEntity<>(reservationMapper.toDTO(updatedReservation), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/court/{courtId}")
    public ResponseEntity<List<ReservationDTOview>> getReservationsByCourt(@PathVariable Long courtId) {
        List<Reservation> reservations = reservationService.getReservationsByCourt(courtId);
        List<ReservationDTOview> reservationDTOs = reservations.stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<List<ReservationDTOview>> getReservationsByPhone(@PathVariable String phoneNumber) {
        List<Reservation> reservations = reservationService.getReservationsByPhone(phoneNumber);
        List<ReservationDTOview> reservationDTOs = reservations.stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);
    }
}
