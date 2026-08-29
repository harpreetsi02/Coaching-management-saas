package com.backend.coaching_saas.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CourseRequest {

    @NotBlank(message = "Course name cannot be empty!")
    private String name;

    @NotBlank(message = "Course description cannot be empty!")
    private String description;

    @NotNull(message = "Course price is required!")
    @Positive(message = "Course price must be greater than 0!")
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
