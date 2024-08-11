package com.example.tennisclubm.dao;

import com.example.tennisclubm.model.SurfaceType;

import java.util.List;

public interface SurfaceTypeDAO {
    SurfaceType findById(Long id);
    List<SurfaceType> findAll();
    SurfaceType save(SurfaceType surfaceType);
    SurfaceType update(SurfaceType surfaceType);
    void delete(Long id);
}
