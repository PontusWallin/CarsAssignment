package com.pontuswallin.carsassignment.controllers.integration_tests;

import com.pontuswallin.carsassignment.services.UserService;
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

import static com.pontuswallin.carsassignment.helpers.TestDTOsBuilder.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsersIfPresent() throws Exception {
        var expected = generateTestUserDTOList();
        when(userService.getAllUsers()).thenReturn(generateTestUserDTOList());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$[0].id").value(expected.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(expected.get(0).getName()))

                .andExpect(jsonPath("$[1].id").value(expected.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(expected.get(1).getName()));
    }

    @Test
    void getAllUsers_shouldReturnEmptyListIfNoUsersArePresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getUserById_shouldReturnSingleUserIfPresent() throws Exception {
        var expected = generateTestUserDTOList().get(0);
        when(userService.getUserById(expected.getId())).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/" + expected.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name").value(expected.getName()));
    }

    @Test
    void getUserById_shouldReturnNullIfNoUserIsPresent() throws Exception {
        var expected = generateTestUserDTOList().get(0);
        when(userService.getUserById(expected.getId())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/" + expected.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnUserAndCarsIfBothArePresent() throws Exception {
        var expected = generateTestUserCarsDTO();
        var expectedUser = expected.getUser();
        var expectedCars = expected.getCars();

        when(userService.getUserAndCarsByUserId(expectedUser.getId())).thenReturn(generateTestUserCarsDTO());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/" + expectedUser.getId() + "/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.user.id").value(expectedUser.getId()))
                .andExpect(jsonPath("$.user.name").value(expectedUser.getName()))

                .andExpect(jsonPath("$.cars").isNotEmpty())
                .andExpect(jsonPath("$.cars").isArray())

                .andExpect(jsonPath("$.cars[0].id").value(expectedCars.get(0).getId()))
                .andExpect(jsonPath("$.cars[0].make").value(expectedCars.get(0).getMake()))
                .andExpect(jsonPath("$.cars[0].numberplate").value(expectedCars.get(0).getNumberplate()))
                .andExpect(jsonPath("$.cars[0].model").value(expectedCars.get(0).getModel()))

                .andExpect(jsonPath("$.cars[1].id").value(expectedCars.get(1).getId()))
                .andExpect(jsonPath("$.cars[1].make").value(expectedCars.get(1).getMake()))
                .andExpect(jsonPath("$.cars[1].numberplate").value(expectedCars.get(1).getNumberplate()))
                .andExpect(jsonPath("$.cars[1].model").value(expectedCars.get(1).getModel()));
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnNoUserAndNoCarsIfNeitherIsPresent() throws Exception {


        when(userService.getUserAndCarsByUserId(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/" + 1L + "/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.user").doesNotExist())
                .andExpect(jsonPath("$.cars").doesNotExist());
    }

    @Test
    void getUserAndCarsByUserId_shouldReturnUserWithNoCarsIfCarsAreNotPresent() throws Exception {
        var expected = generateTestUserCarsDTOWithEmptyCarList();
        var expectedUser = expected.getUser();

        when(userService.getUserAndCarsByUserId(expectedUser.getId())).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/" + expectedUser.getId() + "/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.user.id").value(expectedUser.getId()))
                .andExpect(jsonPath("$.user.name").value(expectedUser.getName()))

                .andExpect(jsonPath("$.cars").isEmpty());
    }
}
