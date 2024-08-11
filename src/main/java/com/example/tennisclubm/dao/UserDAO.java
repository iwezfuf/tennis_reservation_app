package com.example.tennisclubm.dao;

import com.example.tennisclubm.model.User;

import java.util.List;

public interface UserDAO {
    User findById(Long id);
    User findByPhoneNumber(String phoneNumber);
    List<User> findAll();
    User save(User user);
    User update(User user);
    void delete(Long id);
}
