package com.job.lk.webapp.repository;

import com.job.lk.webapp.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    Optional<Enrollment> findByEnrollmentId(Long enrollmentId);

    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    List<Enrollment> findByCourseId(Long courseId);
}
