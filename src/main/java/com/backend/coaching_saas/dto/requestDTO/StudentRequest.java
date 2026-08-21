package com.backend.coaching_saas.dto.requestDTO;

import jakarta.validation.constraints.*;

public class StudentRequest {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @Email(message = "Please enter a valid email!")
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters!")
    private String password;

    @Min(value = 5, message = "Age must be at least 5!")
    private Integer age;

    @NotNull(message = "Course id is required!")
    private Long courseId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
