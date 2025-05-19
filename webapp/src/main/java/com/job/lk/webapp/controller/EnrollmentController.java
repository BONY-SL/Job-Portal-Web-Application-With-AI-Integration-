package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.EnrollmentDTO;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.service.EnrollmentService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<JsonResponse> enrollInCourse(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO createdEnrollmentDTO = enrollmentService.enrollCourse(enrollmentDTO);

        return new ResponseEntity<>(new JsonResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(),
                "Enrolment Success", createdEnrollmentDTO),HttpStatus.CREATED);
    }

    // Get Enrollment by ID
    @GetMapping("/{enrollmentId}")
    public ResponseEntity<JsonResponse> getEnrollment(@PathVariable Long enrollmentId) {
        Optional<EnrollmentDTO> enrollmentDTO = enrollmentService.getEnrollmentById(enrollmentId);

        return new ResponseEntity<>(
                new JsonResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),
                        "Enrolment Success", enrollmentDTO.orElseThrow(()-> new ResourceNotFound("Enrolment Not Found"))),HttpStatus.OK
        );
    }

    // Update Enrollment Progress
    @PutMapping("/{enrollmentId}")
    public ResponseEntity<JsonResponse> updateProgress(@PathVariable Long enrollmentId, @RequestParam Integer progressStatus) {
        Optional<EnrollmentDTO> updatedEnrollmentDTO = enrollmentService.updateEnrollmentProgress(enrollmentId, progressStatus);
        return new ResponseEntity<>(
                new JsonResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(),
                        "Progress Update Success", updatedEnrollmentDTO.orElseThrow(()-> new ResourceNotFound("Enrolment Not Found"))),HttpStatus.CREATED
        );
    }

    // Get All Enrollments for a User
    @GetMapping("/user/{userId}")
    public ResponseEntity<JsonResponse> getUserEnrollments(@PathVariable Long userId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
        return new ResponseEntity<>(
                new JsonResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),
                        Message.SUCCESS.name(), enrollments),HttpStatus.OK
        );
    }

    // Delete Enrollment
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<JsonResponse> deleteEnrollment(@PathVariable Long enrollmentId) {
        boolean deleted = enrollmentService.deleteEnrollment(enrollmentId);
        return new ResponseEntity<>(
                new JsonResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),
                        Message.SUCCESS.name(), deleted),HttpStatus.OK
        );
    }

    // Check Enrollment by User ID and Course ID
    @GetMapping("/check")
    public ResponseEntity<JsonResponse> checkEnrollment(
            @RequestParam Long userId,
            @RequestParam Long courseId) {
        Optional<EnrollmentDTO> enrollmentDTO = enrollmentService.getEnrollmentByUserIdAndCourseId(userId, courseId);
        return new ResponseEntity<>(
                new JsonResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),
                        "Progress Update Success", enrollmentDTO.orElseThrow(()-> new ResourceNotFound("Enrolment Not Found"))),HttpStatus.OK
        );
    }

    // Get all user IDs enrolled in a specific course
    @GetMapping("/course/{courseId}/users")
    public ResponseEntity<JsonResponse> getUserIdsByCourseId(@PathVariable Long courseId) {
        List<Long> userIds = enrollmentService.getUserIdsByCourseId(courseId);
        return new ResponseEntity<>(
                new JsonResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),
                        Message.SUCCESS.name(), userIds),HttpStatus.OK
        );
    }
}
