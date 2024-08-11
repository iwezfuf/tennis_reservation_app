package com.example.tennisclubm.daoImpl;

import com.example.tennisclubm.dao.CourtDAO;
import com.example.tennisclubm.exceptions.ObjectNotFound;
import com.example.tennisclubm.exceptions.ReferenceIdSoftDeletedException;
import com.example.tennisclubm.model.Court;
import com.example.tennisclubm.model.SurfaceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CourtDAOImpl implements CourtDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Court findById(Long id) {
        Court court = entityManager.find(Court.class, id);
        if (court != null && court.isDeleted()) {
            return null;
        }
        return court;
    }

    @Override
    public List<Court> findAll() {
        return entityManager.createQuery("FROM Court WHERE deleted = false", Court.class)
                .getResultList();
    }

    @Override
    public Court save(Court court) {
        if (court.getSurfaceType() != null) {
            SurfaceType surfaceType = entityManager.find(SurfaceType.class, court.getSurfaceType().getId());
            if (surfaceType == null || surfaceType.isDeleted()) {
                throw new ReferenceIdSoftDeletedException("SurfaceType with id " + court.getSurfaceType().getId() + " is soft deleted or does not exist.");
            }
            court.setSurfaceType(surfaceType);
        }
        entityManager.persist(court);
        return court;
    }

    @Override
    public Court update(Court court) {
        if (court.getSurfaceType() != null && court.getSurfaceType().isDeleted()) {
            throw new ReferenceIdSoftDeletedException("SurfaceType with id " + court.getSurfaceType().getId() + " is soft deleted");
        }
        return entityManager.merge(court);
    }

    @Override
    public void delete(Long id) {
        Court court = findById(id);
        if (court == null) {
            throw new ObjectNotFound("Court with id " + id + " does not exist.");
        }
        court.setDeleted(true);
        entityManager.merge(court);
    }

    @Override
    public List<Court> findBySurfaceType(Long surfaceTypeId) {
        return entityManager.createQuery("FROM Court WHERE surfaceType.id = :surface_type_id AND deleted = false", Court.class)
                .setParameter("surface_type_id", surfaceTypeId)
                .getResultList();
    }
}