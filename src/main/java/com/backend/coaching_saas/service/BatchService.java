package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.request.BatchRequest;
import com.backend.coaching_saas.dto.response.BatchResponse;
import com.backend.coaching_saas.dto.response.StudentResponse;
import com.backend.coaching_saas.entity.*;
import com.backend.coaching_saas.exception.*;
import com.backend.coaching_saas.mapper.BatchMapper;
import com.backend.coaching_saas.mapper.StudentMapper;
import com.backend.coaching_saas.repository.BatchRepository;
import com.backend.coaching_saas.repository.CourseRepository;
import com.backend.coaching_saas.repository.StudentRepository;
import com.backend.coaching_saas.repository.UserRepository;
import com.backend.coaching_saas.specification.BatchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public BatchService(
            BatchRepository batchRepository,
            UserRepository userRepository,
            CourseRepository courseRepository,
            StudentRepository studentRepository
    ) {
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public BatchResponse createBatch(BatchRequest request){

        Course course =
                courseRepository.findById(request.getCourseId())
                        .orElseThrow(() ->
                                new CourseNotFoundException(
                                        "Course not found: " + request.getCourseId()
                                )
                        );

        User teacher =
                userRepository.findById(request.getTeacherId())
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "Teacher is not found: " + request.getTeacherId()
                                )
                        );

        if (teacher.getRole() != Role.TEACHER){
            throw new SelectedUserNotTeacherException(
                    "Select user is not a teacher!"
            );
        }

        Batch batch = BatchMapper.toEntity(
                request,
                course,
                teacher
        );

        Batch savedBatch = batchRepository.save(batch);

        return BatchMapper.toResponse(savedBatch);
    }

    @Transactional(readOnly = true)
    public BatchResponse getBatchById(Long id){

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() ->
                        new BatchNotFoundException(
                                "Batch not found: " + id
                        )
                );

        return BatchMapper.toResponse(batch);
    }

    @Transactional(readOnly = true)
    public Page<BatchResponse> getAllBatches(
            Long courseId,
            Long teacherId,
            Pageable pageable
    ) {

        Specification<Batch> specification =
                Specification.unrestricted();

        if (courseId != null) {
            specification = specification.and(
                    BatchSpecification.hasCourseId(courseId)
            );
        }

        if (teacherId != null) {
            specification = specification.and(
                    BatchSpecification.hasTeacherId(teacherId)
            );
        }

        return batchRepository
                .findAll(specification, pageable)
                .map(BatchMapper::toResponse);
    }

    @Transactional
    public BatchResponse enrollStudent(
            Long batchId,
            Long studentId
    ) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new BatchNotFoundException(
                                "Batch not found: " + batchId
                        )
                );

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found: " + studentId
                        )
                );

        if (batchRepository.existsByIdAndStudentsId(batchId, studentId)){
            throw new EnrollmentNotFoundException(
                    "Student is already enrolled in this batch!"
            );
        }

        if (batch.getStudents().size() >= batch.getCapacity()){
            throw new BatchCapacityExceededException(
                    "Batch capacity is full!"
            );
        }

        batch.getStudents().add(student);

        return BatchMapper.toResponse(batch);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getBatchStudents(Long batchId){

        batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new BatchNotFoundException(
                                "Batch not found: " + batchId
                        )
                );

        return batchRepository
                .findStudentsByBatchId(batchId)
                .stream()
                .map(StudentMapper::toResponse)
                .toList();
    }
}
