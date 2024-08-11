package com.example.tennisclubm.controllers;

import com.example.tennisclubm.dto.UserDTOcreate;
import com.example.tennisclubm.dto.UserDTOview;
import com.example.tennisclubm.exceptions.ObjectNotFound;
import com.example.tennisclubm.mappers.UserMapper;
import com.example.tennisclubm.model.User;
import com.example.tennisclubm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:63342")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDTOview> createUser(@RequestBody UserDTOcreate userDTOcreate) {
        try {
            User user = userMapper.toEntity(userDTOcreate);
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(userMapper.toDTO(createdUser), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTOview>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTOview> userDTOs = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTOview> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTOview> updateUser(@PathVariable Long id, @RequestBody UserDTOcreate userDTOcreate) {
        User user = userMapper.toEntity(userDTOcreate);
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(userMapper.toDTO(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        } catch (ObjectNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
