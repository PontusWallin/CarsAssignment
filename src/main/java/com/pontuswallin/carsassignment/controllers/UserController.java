package com.pontuswallin.carsassignment.controllers;

import com.pontuswallin.carsassignment.dto.UserCarsDTO;
import com.pontuswallin.carsassignment.dto.UserDTO;
import com.pontuswallin.carsassignment.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/users/{userId}/cars")
    public UserCarsDTO getUserAndCarsByUserId(@PathVariable Long userId) {
        return userService.getUserAndCarsByUserId(userId);
    }
}