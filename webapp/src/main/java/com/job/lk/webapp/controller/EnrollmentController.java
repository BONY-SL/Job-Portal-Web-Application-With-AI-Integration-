package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.EnrollmentDTO;
import com.job.lk.webapp.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enroll")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enrollInCourse(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO createdEnrollmentDTO = enrollmentService.enrollCourse(enrollmentDTO);
        return ResponseEntity.status(201).body(createdEnrollmentDTO);
    }

    // Get Enrollment by ID
    @GetMapping("/{enrollmentId}")
    public ResponseEntity<EnrollmentDTO> getEnrollment(@PathVariable Long enrollmentId) {
        Optional<EnrollmentDTO> enrollmentDTO = enrollmentService.getEnrollmentById(enrollmentId);
        return enrollmentDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Enrollment Progress
    @PutMapping("/{enrollmentId}")
    public ResponseEntity<EnrollmentDTO> updateProgress(@PathVariable Long enrollmentId, @RequestParam Integer progressStatus) {
        Optional<EnrollmentDTO> updatedEnrollmentDTO = enrollmentService.updateEnrollmentProgress(enrollmentId, progressStatus);
        return updatedEnrollmentDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get All Enrollments for a User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EnrollmentDTO>> getUserEnrollments(@PathVariable Long userId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
        return ResponseEntity.ok(enrollments);
    }

    // Delete Enrollment
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollmentId) {
        boolean deleted = enrollmentService.deleteEnrollment(enrollmentId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Check Enrollment by User ID and Course ID
    @GetMapping("/check")
    public ResponseEntity<EnrollmentDTO> checkEnrollment(
            @RequestParam Long userId,
            @RequestParam Long courseId) {
        Optional<EnrollmentDTO> enrollmentDTO = enrollmentService.getEnrollmentByUserIdAndCourseId(userId, courseId);
        return enrollmentDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all user IDs enrolled in a specific course
    @GetMapping("/course/{courseId}/users")
    public ResponseEntity<List<Long>> getUserIdsByCourseId(@PathVariable Long courseId) {
        List<Long> userIds = enrollmentService.getUserIdsByCourseId(courseId);
        return ResponseEntity.ok(userIds);
    }
}
