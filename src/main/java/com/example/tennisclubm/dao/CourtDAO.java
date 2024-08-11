package com.example.tennisclubm.dao;

import com.example.tennisclubm.model.Court;

import java.util.List;

public interface CourtDAO {
    Court findById(Long id);
    List<Court> findAll();
    Court save(Court court);
    Court update(Court court);
    void delete(Long id);
    List<Court> findBySurfaceType(Long surfaceTypeId);
}
