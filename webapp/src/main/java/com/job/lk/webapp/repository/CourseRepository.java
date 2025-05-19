package com.job.lk.webapp.repository;

import com.job.lk.webapp.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByPublishedBy(Long publishedBy);
}
