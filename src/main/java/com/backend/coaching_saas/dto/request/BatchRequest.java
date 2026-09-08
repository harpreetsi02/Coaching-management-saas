package com.backend.coaching_saas.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public class BatchRequest {

    @NotBlank(message = "Batch name cannot be empty!")
    private String name;

    @NotNull(message = "Course id is required!")
    private Long courseId;

    @NotNull(message = "Teacher id is required!")
    private Long teacherId;

    @NotNull(message = "Start data is required!")
    @FutureOrPresent(message = "Start date cannot be in the past!")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Start time is required!")
    private LocalTime startTime;

    @NotNull(message = "End time is required!")
    private LocalTime endTime;

    @NotNull(message = "Capacity is required!")
    @Positive(message = "Capacity must be greater than 0!")
    private Integer capacity;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
