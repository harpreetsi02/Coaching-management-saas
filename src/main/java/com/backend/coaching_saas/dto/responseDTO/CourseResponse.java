package com.backend.coaching_saas.dto.responseDTO;

import java.util.List;

public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<StudentSummaryResponse> students;

    public CourseResponse(Long id, String name, String description, Double price, List<StudentSummaryResponse> students){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.students = students;
    }

    public List<StudentSummaryResponse> getStudents() {
        return students;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }
}
