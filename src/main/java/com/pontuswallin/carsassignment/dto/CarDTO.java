package com.pontuswallin.carsassignment.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO  implements Serializable {

    private Long id;
    private String make;
    private String model;
    private String numberplate;
}
