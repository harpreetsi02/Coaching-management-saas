package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.requestDTO.StudentRequest;
import com.backend.coaching_saas.dto.responseDTO.StudentResponse;
import com.backend.coaching_saas.entity.Student;
import com.backend.coaching_saas.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public StudentResponse createStudent(StudentRequest request){
        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setAge(request.getAge());

        Student savedStudent = studentRepository.save(student);

        return new StudentResponse(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail(),
                savedStudent.getAge()
        );
    }

    public List<StudentResponse> getAllStudents(){
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getAge()
                ))
                .toList();
    }

    public StudentResponse getStudentById(Long id){
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) return null;

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge()
        );
    }

    public StudentResponse updateStudent(Long id, StudentRequest request){
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) return null;

        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setPassword(request.getPassword());
        existingStudent.setAge(request.getAge());

        Student updateStudent = studentRepository.save(existingStudent);

        return new StudentResponse(
                updateStudent.getId(),
                updateStudent.getName(),
                updateStudent.getEmail(),
                updateStudent.getAge()
        );
    }

    public String deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) return "Student not found!";

        studentRepository.deleteById(id);

        return "Student deleted successfully!";
    }
}
