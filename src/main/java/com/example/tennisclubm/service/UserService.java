package com.example.tennisclubm.service;

import com.example.tennisclubm.dao.UserDAO;
import com.example.tennisclubm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ReservationService reservationService;

    public User getUserById(Long id) {
        return userDAO.findById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User createUser(User user) {
        // If the phone number is already taken, just update the user
        if (isPhoneNumberTaken(user.getPhoneNumber())) {
            User existingUser = findByPhoneNumber(user.getPhoneNumber());
            existingUser.setName(user.getName());
            return updateUser(existingUser);
        }
        return userDAO.save(user);
    }

    public User updateUser(User user) {
        return userDAO.update(user);
    }

    public void deleteUser(Long id) {
        userDAO.delete(id);
        // Delete all reservations for the user
        reservationService.deleteReservationsByUser(id);
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userDAO.findByPhoneNumber(phoneNumber);
    }

    public User createUserByPhoneNumber(String phoneNumber) {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        return userDAO.save(user);
    }

    private boolean isPhoneNumberTaken(String phoneNumber) {
        return findByPhoneNumber(phoneNumber) != null;
    }
}


