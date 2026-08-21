package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.requestDTO.StudentRequest;
import com.backend.coaching_saas.dto.responseDTO.StudentResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.entity.Student;
import com.backend.coaching_saas.exception.CourseNotFoundException;
import com.backend.coaching_saas.exception.EmailAlreadyExistsException;
import com.backend.coaching_saas.exception.StudentNotFoundException;
import com.backend.coaching_saas.mapper.StudentMapper;
import com.backend.coaching_saas.repository.CourseRepository;
import com.backend.coaching_saas.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository){
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public StudentResponse createStudent(StudentRequest request){
        if (studentRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + request.getCourseId()));

        Student student = StudentMapper.toEntity(request);

        student.setCourse(course);

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

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + request.getCourseId()));

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
        existingStudent.setCourse(course);

        Student updateStudent = studentRepository.save(existingStudent);

        return StudentMapper.toResponse(updateStudent);
    }

    public String deleteStudent(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        studentRepository.deleteById(id);

        return "Student deleted successfully!";
    }
}
