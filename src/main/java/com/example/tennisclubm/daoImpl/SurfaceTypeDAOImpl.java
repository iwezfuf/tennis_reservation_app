package com.example.tennisclubm.daoImpl;


import com.example.tennisclubm.dao.SurfaceTypeDAO;
import com.example.tennisclubm.exceptions.ObjectNotFound;
import com.example.tennisclubm.model.SurfaceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class SurfaceTypeDAOImpl implements SurfaceTypeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SurfaceType findById(Long id) {
        SurfaceType surfaceType = entityManager.find(SurfaceType.class, id);
        if (surfaceType != null && surfaceType.isDeleted()) {
            return null;
        }
        return surfaceType;
    }

    @Override
    public List<SurfaceType> findAll() {
        return entityManager.createQuery("FROM SurfaceType WHERE deleted = false", SurfaceType.class)
                .getResultList();
    }

    @Override
    public SurfaceType save(SurfaceType surfaceType) {
        entityManager.persist(surfaceType);
        return surfaceType;
    }

    @Override
    public SurfaceType update(SurfaceType surfaceType) {
        return entityManager.merge(surfaceType);
    }

    @Override
    public void delete(Long id) {
        SurfaceType surfaceType = findById(id);
        if (surfaceType == null) {
            throw new ObjectNotFound("SurfaceType with id " + id + " does not exist.");
        }
        surfaceType.setDeleted(true);
        entityManager.merge(surfaceType);
    }
}

