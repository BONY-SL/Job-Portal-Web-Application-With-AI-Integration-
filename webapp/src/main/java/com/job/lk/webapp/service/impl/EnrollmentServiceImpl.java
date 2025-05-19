package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.EnrollmentDTO;
import com.job.lk.webapp.entity.Enrollment;
import com.job.lk.webapp.repository.EnrollmentRepository;
import com.job.lk.webapp.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;


    // Create Enrollment
    public EnrollmentDTO enrollCourse(EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(enrollmentDTO.getCourseId());
        enrollment.setUserId(enrollmentDTO.getUserId());
        enrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        enrollment.setProgressStatus(enrollmentDTO.getProgressStatus());

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // Convert saved entity to DTO
        return mapToDTO(savedEnrollment);
    }

    // Get Enrollment by ID
    public Optional<EnrollmentDTO> getEnrollmentById(Long enrollmentId) {
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByEnrollmentId(enrollmentId);
        return enrollmentOptional.map(this::mapToDTO);
    }

    // Update Enrollment Progress
    public Optional<EnrollmentDTO> updateEnrollmentProgress(Long enrollmentId, Integer progressStatus) {
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByEnrollmentId(enrollmentId);
        if (enrollmentOptional.isPresent()) {
            Enrollment enrollment = enrollmentOptional.get();
            enrollment.setProgressStatus(progressStatus);
            Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
            return Optional.of(mapToDTO(updatedEnrollment));
        }
        return Optional.empty();
    }

    // Get All Enrollments for a User
    public List<EnrollmentDTO> getEnrollmentsByUserId(Long userId) {
        return enrollmentRepository.findAll().stream()
                .filter(e -> e.getUserId().equals(userId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Delete Enrollment
    public boolean deleteEnrollment(Long enrollmentId) {
        Optional<Enrollment> enrollment = enrollmentRepository.findByEnrollmentId(enrollmentId);
        if (enrollment.isPresent()) {
            enrollmentRepository.delete(enrollment.get());
            return true;
        }
        return false;
    }


    public List<Long> getUserIdsByCourseId(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        List<Long> userIds = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            userIds.add(enrollment.getUserId());
        }

        return userIds;
    }



    // Helper method to convert Enrollment entity to EnrollmentDTO
    private EnrollmentDTO mapToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setEnrollmentId(enrollment.getEnrollmentId());
        dto.setCourseId(enrollment.getCourseId());
        dto.setUserId(enrollment.getUserId());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setProgressStatus(enrollment.getProgressStatus());
        return dto;
    }


    public Optional<EnrollmentDTO> getEnrollmentByUserIdAndCourseId(Long userId, Long courseId) {
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        return enrollmentOptional.map(this::mapToDTO);
    }
}
