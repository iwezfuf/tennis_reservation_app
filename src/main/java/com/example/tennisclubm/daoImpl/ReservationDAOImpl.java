package com.example.tennisclubm.daoImpl;

import com.example.tennisclubm.dao.ReservationDAO;
import com.example.tennisclubm.exceptions.ObjectNotFound;
import com.example.tennisclubm.exceptions.ReferenceIdSoftDeletedException;
import com.example.tennisclubm.model.Court;
import com.example.tennisclubm.model.Reservation;
import com.example.tennisclubm.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ReservationDAOImpl implements ReservationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reservation> findById(Long id) {
        Optional<Reservation> reservation = Optional.ofNullable(entityManager.find(Reservation.class, id));
        return reservation.filter(value -> !value.isDeleted());
    }

    @Override
    public List<Reservation> findAll() {
        return entityManager.createQuery("FROM Reservation WHERE deleted = false", Reservation.class)
                .getResultList();
    }

    @Override
    public List<Reservation> findByCourtId(Long courtId) {
        return entityManager.createQuery("FROM Reservation WHERE court.id = :court_id AND deleted = false", Reservation.class)
                .setParameter("court_id", courtId)
                .getResultList();
    }

    @Override
    public List<Reservation> findByPhoneNumber(String phoneNumber) {
        return entityManager.createQuery("FROM Reservation WHERE user.phoneNumber = :phoneNumber AND deleted = false", Reservation.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return entityManager.createQuery("FROM Reservation WHERE user.id = :user_id AND deleted = false", Reservation.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getCourt() != null) {
            Court court = entityManager.find(Court.class, reservation.getCourt().getId());
            if (court == null || court.isDeleted()) {
                throw new ReferenceIdSoftDeletedException("Court with id " + reservation.getCourt().getId() + " is soft deleted or does not exist.");
            }
            reservation.setCourt(court);
        }
        if (reservation.getUser() != null) {
            User user = entityManager.find(User.class, reservation.getUser().getId());
            if (user == null || user.isDeleted()) {
                throw new ReferenceIdSoftDeletedException("User with id " + reservation.getUser().getId() + " is soft deleted or does not exist.");
            }
            reservation.setUser(user);
        }
        entityManager.persist(reservation);
        return reservation;
    }

    @Override
    public Reservation update(Reservation reservation) {
        return entityManager.merge(reservation);
    }

    @Override
    public void delete(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation == null) {
            throw new ObjectNotFound("Reservation with id " + id + " does not exist.");
        }
        reservation.setDeleted(true);
        entityManager.merge(reservation);
    }

    @Override
    public boolean existsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        String query = "SELECT count(r) FROM Reservation r WHERE (r.startTime < :endTime AND r.endTime > :startTime AND r.deleted = false)";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<Reservation> findBySurfaceType(Long surfaceTypeId) {
        return entityManager.createQuery("FROM Reservation WHERE court.surfaceType.id = :surface_type_id AND deleted = false", Reservation.class)
                .setParameter("surface_type_id", surfaceTypeId)
                .getResultList();
    }
}
