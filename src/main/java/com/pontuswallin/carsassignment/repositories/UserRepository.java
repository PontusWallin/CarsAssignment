package com.pontuswallin.carsassignment.repositories;

import com.pontuswallin.carsassignment.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}
