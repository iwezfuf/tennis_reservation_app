package com.example.tennisclubm.daoImpl;

import com.example.tennisclubm.dao.UserDAO;
import com.example.tennisclubm.exceptions.ObjectNotFound;
import com.example.tennisclubm.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null && user.isDeleted()) {
            return null;
        }
        return user;
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        try {
            return entityManager.createQuery("FROM User WHERE phoneNumber = :phoneNumber AND deleted = false", User.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("FROM User WHERE deleted = false", User.class)
                .getResultList();
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        if (user == null) {
            throw new ObjectNotFound("User with id " + id + " does not exist.");
        }
        user.setDeleted(true);
        entityManager.merge(user);
    }
}

