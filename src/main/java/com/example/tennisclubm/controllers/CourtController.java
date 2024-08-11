package com.example.tennisclubm.controllers;

import com.example.tennisclubm.dto.CourtDTOcreate;
import com.example.tennisclubm.dto.CourtDTOview;
import com.example.tennisclubm.exceptions.ObjectNotFound;
import com.example.tennisclubm.mappers.CourtMapper;
import com.example.tennisclubm.model.Court;
import com.example.tennisclubm.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courts")
@CrossOrigin(origins = "http://localhost:63342")
public class CourtController {

    @Autowired
    private CourtService courtService;

    @Autowired
    private CourtMapper courtMapper;

    @PostMapping
    public ResponseEntity<CourtDTOview> createCourt(@RequestBody CourtDTOcreate courtDTOcreate) {
        try {
            Court court = courtMapper.toEntity(courtDTOcreate);
            Court createdCourt = courtService.createCourt(court);
            return new ResponseEntity<>(courtMapper.toDTO(createdCourt), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourtDTOview>> getAllCourts() {
        List<Court> courts = courtService.getAllCourts();
        List<CourtDTOview> courtDTOs = courts.stream()
                .map(courtMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(courtDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtDTOview> getCourtById(@PathVariable Long id) {
        Court court = courtService.getCourtById(id);
        return new ResponseEntity<>(courtMapper.toDTO(court), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(@PathVariable Long id) {
        try {
            courtService.deleteCourt(id);
        } catch (ObjectNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtDTOview> updateCourt(@PathVariable Long id, @RequestBody CourtDTOcreate courtDTOcreate) {
        try {
            Court court = courtMapper.toEntity(courtDTOcreate);
            court.setId(id);
            Court updatedCourt = courtService.updateCourt(court);
            return new ResponseEntity<>(courtMapper.toDTO(updatedCourt), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
