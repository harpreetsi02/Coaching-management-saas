package com.backend.coaching_saas.controller;

import com.backend.coaching_saas.dto.request.BatchRequest;
import com.backend.coaching_saas.dto.response.BatchResponse;
import com.backend.coaching_saas.dto.response.PageResponse;
import com.backend.coaching_saas.dto.response.StudentResponse;
import com.backend.coaching_saas.service.BatchService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batches")
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService){
        this.batchService = batchService;
    }

    @PostMapping
    public ResponseEntity<BatchResponse> createBatch(
            @Valid @RequestBody BatchRequest request
    ) {
        BatchResponse response =
                batchService.createBatch(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchResponse> getBatchById(
            @PathVariable Long id
    ) {
        BatchResponse response = batchService.getBatchById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<BatchResponse>> getAllBatches(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long teacherId,
            Pageable pageable
    ) {

        Page<BatchResponse> page =
                batchService.getAllBatches(courseId, teacherId, pageable);

        return ResponseEntity.ok(
                new PageResponse<>(page)
        );
    }

    @PostMapping("/{batchId}/students/{studentId}")
    public ResponseEntity<BatchResponse> enrollStudent(
            @PathVariable Long batchId,
            @PathVariable Long studentId
    ) {

        BatchResponse response =
                batchService.enrollStudent(batchId, studentId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{batchId}/students")
    public ResponseEntity<List<StudentResponse>> getBatchStudents(
            @PathVariable Long batchId
    ) {
        return ResponseEntity.ok(
                batchService.getBatchStudents(batchId)
        );
    }
}
