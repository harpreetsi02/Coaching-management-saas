package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.requestDTO.StudentRequest;
import com.backend.coaching_saas.dto.responseDTO.StudentResponse;
import com.backend.coaching_saas.entity.Student;
import com.backend.coaching_saas.exception.EmailAlreadyExistsException;
import com.backend.coaching_saas.exception.StudentNotFoundException;
import com.backend.coaching_saas.mapper.StudentMapper;
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
        if (studentRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        Student student = StudentMapper.toEntity(request);

        Student savedStudent = studentRepository.save(student);

        return StudentMapper.toResponse(savedStudent);
    }

    public List<StudentResponse> getAllStudents(){
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(StudentMapper::toResponse)
                .toList();
    }

    public StudentResponse getStudentById(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        return StudentMapper.toResponse(student);
    }

    public StudentResponse updateStudent(Long id, StudentRequest request){
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        if (!existingStudent.getEmail().equals(request.getEmail())
                && studentRepository.existsByEmail(request.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "Email already exists: " + request.getEmail()
            );
        }

        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setPassword(request.getPassword());
        existingStudent.setAge(request.getAge());

        Student updateStudent = studentRepository.save(existingStudent);

        return StudentMapper.toResponse(updateStudent);
    }

    public String deleteStudent(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        if (student == null) return "Student not found!";

        studentRepository.deleteById(id);

        return "Student deleted successfully!";
    }
}
