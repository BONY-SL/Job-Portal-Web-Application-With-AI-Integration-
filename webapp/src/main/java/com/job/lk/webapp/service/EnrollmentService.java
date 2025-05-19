package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.EnrollmentDTO;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    EnrollmentDTO enrollCourse(EnrollmentDTO enrollmentDTO);

    Optional<EnrollmentDTO> getEnrollmentById(Long enrollmentId);

    Optional<EnrollmentDTO> updateEnrollmentProgress(Long enrollmentId, Integer progressStatus);

    List<EnrollmentDTO> getEnrollmentsByUserId(Long userId);

    boolean deleteEnrollment(Long enrollmentId);

    Optional<EnrollmentDTO> getEnrollmentByUserIdAndCourseId(Long userId, Long courseId);

    List<Long> getUserIdsByCourseId(Long courseId);
}
