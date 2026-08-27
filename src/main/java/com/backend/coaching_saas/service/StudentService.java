package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.requestDTO.StudentRequest;
import com.backend.coaching_saas.dto.responseDTO.PageResponse;
import com.backend.coaching_saas.dto.responseDTO.StudentResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.entity.Student;
import com.backend.coaching_saas.exception.CourseNotFoundException;
import com.backend.coaching_saas.exception.EmailAlreadyExistsException;
import com.backend.coaching_saas.exception.StudentNotFoundException;
import com.backend.coaching_saas.mapper.StudentMapper;
import com.backend.coaching_saas.repository.CourseRepository;
import com.backend.coaching_saas.repository.StudentRepository;
import com.backend.coaching_saas.specification.StudentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(
            StudentRepository studentRepository,
            CourseRepository courseRepository
    ) {
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

    @Transactional(readOnly = true)
    public PageResponse<StudentResponse> getAllStudents(Pageable pageable){
        Page<Student> students = studentRepository.findAll(pageable);

        Page<StudentResponse> studentPage = students.map(StudentMapper::toResponse);

        return new PageResponse<>(studentPage);
    }

    @Transactional(readOnly = true)
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

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + request.getCourseId()));


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

    @Transactional(readOnly = true)
    public Student getStudentEntityById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public PageResponse<StudentResponse> searchStudentByName(String name, Pageable pageable){
        Page<Student> students = studentRepository.findByNameContainingIgnoreCase(name, pageable);

        Page<StudentResponse> studentPage = students.map(StudentMapper::toResponse);

        return new PageResponse<>(studentPage);
    }

    @Transactional(readOnly = true)
    public PageResponse<StudentResponse> searchStudentsWithSpecification(
            String name,
            Integer age,
            Pageable pageable
    ) {

        Specification<Student> specification = null;

        if (name != null && !name.isBlank()) {
            specification = StudentSpecification.hasName(name);
        }

        if (age != null) {
            Specification<Student> ageSpecification =
                    StudentSpecification.hasAge(age);

            if (specification == null){
                specification = ageSpecification;
            } else {
                specification = specification.and(ageSpecification);
            }
        }

        Page<Student> students;

        if (specification == null){
            students = studentRepository.findAll(pageable);
        } else {
            students = studentRepository.findAll(specification, pageable);
        }

        Page<StudentResponse> studentPage =
                students.map(StudentMapper::toResponse);

        return new PageResponse<>(studentPage);
    }
}
