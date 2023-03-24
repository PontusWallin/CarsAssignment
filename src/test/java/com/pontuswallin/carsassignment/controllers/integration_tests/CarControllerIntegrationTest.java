package com.pontuswallin.carsassignment.controllers.integration_tests;

import com.pontuswallin.carsassignment.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.pontuswallin.carsassignment.helpers.TestDTOsBuilder.generateTestCarDTOs;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCars_shouldReturnEmptyCarListIfNoCarsArePresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAllCars_shouldReturnCarsIfCarsArePresent() throws Exception {
        var expected = generateTestCarDTOs();
        when(carService.getAllCars()).thenReturn(generateTestCarDTOs());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cars")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$[0].id").value(expected.get(0).getId()))
                .andExpect(jsonPath("$[0].make").value(expected.get(0).getMake()))
                .andExpect(jsonPath("$[0].model").value(expected.get(0).getModel()))
                .andExpect(jsonPath("$[0].numberplate").value(expected.get(0).getNumberplate()))

                .andExpect(jsonPath("$[1].id").value(expected.get(1).getId()))
                .andExpect(jsonPath("$[1].make").value(expected.get(1).getMake()))
                .andExpect(jsonPath("$[1].model").value(expected.get(1).getModel()))
                .andExpect(jsonPath("$[1].numberplate").value(expected.get(1).getNumberplate()));
    }

    @Test
    void getCarById_shouldReturnSingleCarIfCarIsPresent() throws Exception {
        var expected = generateTestCarDTOs().get(0);
        when(carService.getCarById(expected.getId())).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cars/" + expected.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())

                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.make").value(expected.getMake()))
                .andExpect(jsonPath("$.model").value(expected.getModel()))
                .andExpect(jsonPath("$.numberplate").value(expected.getNumberplate()));
    }

    @Test
    void getCarById_shouldReturnNullIfCarIsNotPresent() throws Exception {
        var expected = generateTestCarDTOs().get(0);
        when(carService.getCarById(expected.getId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cars/" + expected.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
