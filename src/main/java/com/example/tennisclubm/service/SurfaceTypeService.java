package com.example.tennisclubm.service;

import com.example.tennisclubm.dao.SurfaceTypeDAO;
import com.example.tennisclubm.model.SurfaceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurfaceTypeService {

    @Autowired
    private SurfaceTypeDAO surfaceTypeDAO;

    @Autowired
    private CourtService courtService;

    public List<SurfaceType> getAllSurfaceTypes() {
        return surfaceTypeDAO.findAll();
    }

    public SurfaceType getSurfaceTypeById(Long id) {
        return surfaceTypeDAO.findById(id);
    }

    public SurfaceType createSurfaceType(SurfaceType surfaceType) {
        return surfaceTypeDAO.save(surfaceType);
    }

    public void deleteSurfaceType(Long id) {
        surfaceTypeDAO.delete(id);
        // Delete all courts with the surface type
        courtService.deleteCourtsBySurfaceType(id);
    }
}
