package com.pontuswallin.carsassignment.services;

import com.pontuswallin.carsassignment.domain.User;
import com.pontuswallin.carsassignment.dto.CarDTO;
import com.pontuswallin.carsassignment.dto.UserCarsDTO;
import com.pontuswallin.carsassignment.dto.UserDTO;
import com.pontuswallin.carsassignment.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CarService carService;

    private final ModelMapper mapper;
    public UserService(UserRepository userRepository, CarService carService, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.carService = carService;
        this.mapper = mapper;
    }

    public List<UserDTO> getAllUsers() {
        Iterable<User> allUsers = userRepository.findAll();
        return StreamSupport.stream(allUsers.spliterator(), false)
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        return mapper.map(user, UserDTO.class);
    }

    public UserCarsDTO getUserAndCarsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            return null;
        }

        UserDTO userDTO = mapper.map(user, UserDTO.class);

        List<CarDTO> carsDTO = carService.getCarsByUserId(userId);

        UserCarsDTO userCarsDTO = new UserCarsDTO();
        userCarsDTO.setUser(userDTO);
        userCarsDTO.setCars(carsDTO);

        return userCarsDTO;
    }
}
