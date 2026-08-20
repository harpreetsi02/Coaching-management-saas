package com.backend.coaching_saas.service;

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

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Long id, Student student){
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent != null){
            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setPassword(student.getPassword());
            existingStudent.setAge(student.getAge());

            return studentRepository.save(existingStudent);
        }

        return null;
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }
}
