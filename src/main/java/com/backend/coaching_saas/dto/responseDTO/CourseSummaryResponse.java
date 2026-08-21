package com.backend.coaching_saas.dto.responseDTO;

public class CourseSummaryResponse {

    private Long id;
    private String name;

    public CourseSummaryResponse(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
