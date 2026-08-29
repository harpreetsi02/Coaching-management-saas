package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.request.StudentRequest;
import com.backend.coaching_saas.dto.response.StudentResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.entity.Student;
import com.backend.coaching_saas.mapper.StudentMapper;
import com.backend.coaching_saas.repository.CourseRepository;
import com.backend.coaching_saas.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(
            StudentRepository studentRepository,
            CourseRepository courseRepository) {

        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {

        List<Course> courses =
                courseRepository.findAllById(request.getCourseIds());

        Student student =
                StudentMapper.toEntity(request, courses);

        Student savedStudent =
                studentRepository.save(student);

        return StudentMapper.toResponse(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        return StudentMapper.toResponse(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents(){
        List<StudentResponse> responses = studentRepository.findAll()
                .stream()
                .map(StudentMapper::toResponse)
                .toList();

        return responses;
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request){

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        List<Course> courses = courseRepository.findAllById(request.getCourseIds());

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setAge(request.getAge());
        student.setCourses(courses);

        Student updateStudent = studentRepository.save(student);

        return StudentMapper.toResponse(updateStudent);
    }

    @Transactional
    public void deleteStudent(Long id){

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        studentRepository.deleteById(id);
    }
}
