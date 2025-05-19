package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO);

    CourseDTO updateCourse(Long id, CourseDTO dto);

    List<CourseDTO> getAllCourses();

    CourseDTO getCourseById(Long id);

    CourseDTO togglePublished(Long id);

    boolean deleteCourse(Long id);

    List<CourseDTO> getCoursesByPublishedBy(Long userId);
}
