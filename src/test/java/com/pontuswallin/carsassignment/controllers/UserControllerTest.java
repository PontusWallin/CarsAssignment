package com.pontuswallin.carsassignment.controllers;

import com.pontuswallin.carsassignment.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.pontuswallin.carsassignment.helpers.TestDTOsBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    UserService userService;

    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void getAllUsers_shouldReturnUsersIfUsersArePresent() {
        var expected = generateTestUserDTOList();
        when(userService.getAllUsers()).thenReturn(generateTestUserDTOList());
        var actual = userController.getAllUsers();

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getAllUsers_shouldReturnEmptyListIfNoUsersArePresent() {
        var actual = userController.getAllUsers();
        assertNotNull(actual);
        assertEquals(0, actual.size());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_shouldReturnSingleUserIfUserIsPresent() {
        var expected = generateSingleTestUserDTO();
        when(userService.getUserById(1L)).thenReturn(generateSingleTestUserDTO());
        var actual = userController.getUserById(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserById_shouldReturnNullIfNoUserIsPresent() {
        var actual = userController.getUserById(1L);
        assertNull(actual);

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnUserAndCarsIfBothArePresent() {
        var expected = generateTestUserCarsDTO();
        when(userService.getUserAndCarsByUserId(1L)).thenReturn(generateTestUserCarsDTO());
        var actual = userController.getUserAndCarsByUserId(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userService, times(1)).getUserAndCarsByUserId(1L);
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnNoUserAndNoCarsIfNeitherIsPresent() {
        var actual = userController.getUserAndCarsByUserId(1L);
        assertNull(actual);

        verify(userService, times(1)).getUserAndCarsByUserId(1L);
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnUserWithNoCarsIfCarsAreNotPresent() {
        var expected = generateTestUserCarsDTOWithEmptyCarList();
        when(userService.getUserAndCarsByUserId(1L))
                .thenReturn(generateTestUserCarsDTOWithEmptyCarList());

        var actual = userController.getUserAndCarsByUserId(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userService, times(1)).getUserAndCarsByUserId(1L);
    }
}