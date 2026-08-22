package com.backend.coaching_saas.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CourseRequest {

    @NotBlank(message = "Please enter course name!")
    private String name;

    @NotBlank(message = "Enter description")
    @Size(min = 5)
    private String description;

    @NotNull(message = "Enter valid amount!")
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}