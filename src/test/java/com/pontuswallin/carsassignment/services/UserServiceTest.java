package com.pontuswallin.carsassignment.services;

import com.pontuswallin.carsassignment.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.pontuswallin.carsassignment.helpers.TestDTOsBuilder.*;
import static com.pontuswallin.carsassignment.helpers.TestModelsBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CarService carService;

    ModelMapper mapper = new ModelMapper();

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, carService, mapper);
    }

    @Test
    void getAllUsers_shouldReturnUsersIfUsersArePresent() {
        var expected = generateTestUserDTOList();
        when(userRepository.findAll()).thenReturn(generateTestUserModelList());
        var actual = userService.getAllUsers();

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers_shouldReturnEmptyListIfNoUsersArePresent() {
        when(userRepository.findAll()).thenReturn(List.of());
        var actual = userService.getAllUsers();

        assertNotNull(actual);
        assertEquals(0, actual.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_shouldReturnSingleUserIfUserIsPresent() {
        var expected = generateSingleTestUserDTO();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(generateSingleTestUserModel()));
        var actual = userService.getUserById(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_shouldReturnNullIfNoUserIsPresent() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        var actual = userService.getUserById(1L);

        assertNull(actual);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnUserAndCarsIfBothArePresent() {
        var expected = generateTestUserCarsDTO();
        when(userRepository.findById(1L)).thenReturn(Optional.of(generateSingleTestUserModel()));
        when(carService.getCarsByUserId(1L)).thenReturn(generateTestCarDTOs());
        var actual = userService.getUserAndCarsByUserId(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(1L);
        verify(carService, times(1)).getCarsByUserId(1L);
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnNoUserAndNoCarsIfNeitherIsPresent() {
        var actual = userService.getUserAndCarsByUserId(1L);

        assertNull(actual);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnUserWithNoCarsIfCarsAreNotPresent() {
        var expected = generateTestUserCarsDTOWithEmptyCarList();
        when(userRepository.findById(1L)).thenReturn(Optional.of(generateSingleTestUserModel()));
        when(carService.getCarsByUserId(1L)).thenReturn(List.of());

        var actual = userService.getUserAndCarsByUserId(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(1L);
    }
}