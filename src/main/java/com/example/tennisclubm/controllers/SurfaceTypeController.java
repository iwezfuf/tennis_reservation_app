package com.example.tennisclubm.controllers;

import com.example.tennisclubm.dto.SurfaceTypeDTOcreate;
import com.example.tennisclubm.dto.SurfaceTypeDTOview;
import com.example.tennisclubm.exceptions.ObjectNotFound;
import com.example.tennisclubm.mappers.SurfaceTypeMapper;
import com.example.tennisclubm.model.SurfaceType;
import com.example.tennisclubm.service.SurfaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/surfacetype")
@CrossOrigin(origins = "http://localhost:63342")
public class SurfaceTypeController {

    @Autowired
    private SurfaceTypeService surfaceTypeService;

    @Autowired
    private SurfaceTypeMapper surfaceTypeMapper;

    @PostMapping
    public ResponseEntity<SurfaceTypeDTOview> createSurfaceType(@RequestBody SurfaceTypeDTOcreate surfaceTypeDTOcreate) {
        SurfaceType surfaceType = surfaceTypeMapper.toEntity(surfaceTypeDTOcreate);
        SurfaceType createdSurfaceType = surfaceTypeService.createSurfaceType(surfaceType);
        return new ResponseEntity<>(surfaceTypeMapper.toDTO(createdSurfaceType), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SurfaceTypeDTOview>> getAllSurfaceTypes() {
        List<SurfaceType> surfaceTypes = surfaceTypeService.getAllSurfaceTypes();
        List<SurfaceTypeDTOview> surfaceTypeDTOs = surfaceTypes.stream()
                .map(surfaceTypeMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(surfaceTypeDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurfaceTypeDTOview> getSurfaceTypeById(@PathVariable Long id) {
        SurfaceType surfaceType = surfaceTypeService.getSurfaceTypeById(id);
        return new ResponseEntity<>(surfaceTypeMapper.toDTO(surfaceType), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurfaceType(@PathVariable Long id) {
        try {
            surfaceTypeService.deleteSurfaceType(id);
        } catch (ObjectNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
