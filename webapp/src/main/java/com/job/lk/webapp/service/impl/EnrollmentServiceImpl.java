package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.EnrollmentDTO;
import com.job.lk.webapp.entity.Enrollment;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.repository.EnrollmentRepository;
import com.job.lk.webapp.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper modelMapper;


    // Create Enrollment
    public EnrollmentDTO enrollCourse(EnrollmentDTO enrollmentDTO) {
        var enrollment = Enrollment.builder()
                .courseId(enrollmentDTO.getCourseId()).userId(enrollmentDTO.getUserId())
                .enrollmentDate(enrollmentDTO.getEnrollmentDate()).progressStatus(enrollmentDTO.getProgressStatus())
                .build();
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return modelMapper.map(savedEnrollment, EnrollmentDTO.class);
    }

    // Get Enrollment by ID
    public Optional<EnrollmentDTO> getEnrollmentById(Long enrollmentId) {
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByEnrollmentId(enrollmentId);
        return Optional.ofNullable(modelMapper.map(enrollmentOptional, EnrollmentDTO.class));
    }

    // Update Enrollment Progress
    public Optional<EnrollmentDTO> updateEnrollmentProgress(Long enrollmentId, Integer progressStatus) {
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByEnrollmentId(enrollmentId);
        if (enrollmentOptional.isPresent()) {
            Enrollment enrollment = enrollmentOptional.get();
            enrollment.setProgressStatus(progressStatus);
            Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
            return Optional.ofNullable(modelMapper.map(updatedEnrollment, EnrollmentDTO.class));
        }
        return Optional.empty();
    }

    // Get All Enrollments for a User
    public List<EnrollmentDTO> getEnrollmentsByUserId(Long userId) {
        return enrollmentRepository.findAll().stream()
                .filter(e -> e.getUserId().equals(userId))
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
                .collect(Collectors.toList());
    }

    // Delete Enrollment
    public boolean deleteEnrollment(Long enrollmentId) {
        Optional<Enrollment> enrollment = enrollmentRepository.findByEnrollmentId(enrollmentId);
        if (enrollment.isPresent()) {
            enrollmentRepository.delete(enrollment.get());
            return true;
        }
        throw new ResourceNotFound("Enrolment Not Found....");
    }

    public List<Long> getUserIdsByCourseId(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        List<Long> userIds = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            userIds.add(enrollment.getUserId());
        }

        return userIds;
    }
    public Optional<EnrollmentDTO> getEnrollmentByUserIdAndCourseId(Long userId, Long courseId) {
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        return Optional.ofNullable(modelMapper.map(enrollmentOptional, EnrollmentDTO.class));
    }
}
