package com.pontuswallin.carsassignment.repositories;

import com.pontuswallin.carsassignment.domain.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByUserId(Long personId);
}