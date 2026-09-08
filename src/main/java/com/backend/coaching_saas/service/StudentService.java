package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.request.StudentRequest;
import com.backend.coaching_saas.dto.response.CourseResponse;
import com.backend.coaching_saas.dto.response.StudentResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.entity.Student;
import com.backend.coaching_saas.exception.*;
import com.backend.coaching_saas.mapper.CourseMapper;
import com.backend.coaching_saas.mapper.StudentMapper;
import com.backend.coaching_saas.repository.CourseRepository;
import com.backend.coaching_saas.repository.StudentRepository;
import com.backend.coaching_saas.specification.StudentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        List<Course> courses =
                courseRepository.findAllById(request.getCourseIds());

        if (courses.size() != request.getCourseIds().size()){
            throw new CourseNotFoundException(
                    "One or more course IDs are invalid!"
            );
        }

        Student student = StudentMapper.toEntity(
                request,
                courses,
                passwordEncoder.encode(request.getPassword())
        );

        Student savedStudent =
                studentRepository.save(student);

        return StudentMapper.toResponse(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found!"));

        return StudentMapper.toResponse(student);
    }

    @Transactional(readOnly = true)
    public Page<StudentResponse> getAllStudents(
            String name,
            String email,
            Integer age,
            Integer minAge,
            Integer maxAge,
            Pageable pageable
    ) {

        Specification<Student> specification = null;

        if (name != null && !name.isBlank()){
            specification = StudentSpecification.hasName(name);
        }

        if (email != null && !email.isBlank()){
            Specification<Student> emailSpec =
                    StudentSpecification.hasEmail(email);

            specification = specification == null
                    ? emailSpec
                    : specification.and(emailSpec);
        }

        if (age != null) {
            Specification<Student> ageSpec = StudentSpecification.hasAge(age);

            if (specification == null) {
                specification = ageSpec;
            } else {
                specification = specification.and(ageSpec);
            }
        }

        if (minAge != null){
            Specification<Student> spec =
                    StudentSpecification.ageGreaterThanOrEqual(minAge);

            specification = specification == null
                    ? spec
                    : specification.and(spec);
        }

        if (maxAge != null){
            Specification<Student> spec =
                    StudentSpecification.ageLowerThanOrEqual(maxAge);

            specification = specification == null
                    ? spec
                    : specification.and(spec);
        }

        return studentRepository
                .findAll(specification, pageable)
                .map(StudentMapper::toResponse);
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request){

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                            "Student not found!"
                        )
                );

        if (studentRepository.existsByEmailAndIdNot(
                request.getEmail(), id)
        ) {
            throw new EmailAlreadyExistsException(
                    "Email already exists!"
            );
        }

        List<Course> courses = courseRepository.findAllById(request.getCourseIds());

        if (courses.size() != request.getCourseIds().size()) {
            throw new CourseNotFoundException(
                    "One or more courses not found!"
            );
        }

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        student.setAge(request.getAge());
        student.setCourses(courses);

        return StudentMapper.toResponse(student);
    }

    @Transactional
    public void deleteStudent(Long id){

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found:" + id
                        )
                );

        studentRepository.deleteById(id);
    }

    @Transactional
    public StudentResponse enrollStudent(
            Long studentId,
            Long courseId
    ) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found: " + studentId
                        )
                );

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException(
                                "Course not found: " + courseId
                        )
                );

        if (studentRepository.existsByIdAndCoursesId(
                studentId,
                courseId
        )) {
            throw new EnrollmentAlreadyExistsException(
                    "Student is already enrolled in this course!"
            );
        }

        student.getCourses().add(course);

        return StudentMapper.toResponse(student);
    }

    @Transactional
    public StudentResponse unenrollStudent(
            Long studentId,
            Long courseId
    ) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found: " + studentId
                        )
                );

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException(
                                "Course not found: " + courseId
                        )
                );

        if (!student.getCourses().contains(course)) {
            throw new EnrollmentNotFoundException(
                    "Student is not enrolled in this course!"
            );
        }

        student.getCourses().remove(course);

        return StudentMapper.toResponse(student);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getEnrolledCourses(Long studentId){

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found: " + studentId
                        )
                );

        return student.getCourses()
                .stream()
                .map(CourseMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getEnrolledStudents(Long courseId){

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException(
                                "Course not found: " + courseId
                        )
                );

        return courseRepository
                .findStudentsByCourseId(courseId)
                .stream()
                .map(StudentMapper::toResponse)
                .toList();
    }
}
