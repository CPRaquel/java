package com.rcaspe.CarRegistry.controller;

import com.rcaspe.CarRegistry.controller.mapper.CarMapper;
import com.rcaspe.CarRegistry.domain.Car;
import com.rcaspe.CarRegistry.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarController carController;

    @MockBean
    private CarService carService;

    @MockBean
    private CarMapper carMapper;

    @Test
    //@WithMockUser(username = "test", password = "test", roles = "CLIENT")
    void getCar_test() throws Exception {
        // Given
        Car car = new Car();
        car.setModel("Ateca");

        // When
        when(carService.getCarById(1)).thenReturn(car);

        // Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/car/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.model", "Ateca"));


    }





}
