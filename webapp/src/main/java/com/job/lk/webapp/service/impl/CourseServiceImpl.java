package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.CourseDTO;
import com.job.lk.webapp.entity.Course;
import com.job.lk.webapp.entity.User;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.exception.coustom.UserNotFoundError;
import com.job.lk.webapp.repository.CourseRepository;
import com.job.lk.webapp.repository.UserRepository;
import com.job.lk.webapp.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {

        User user = userRepository.findById(courseDTO.getPublishedBy()).orElseThrow(
                ()->new UserNotFoundError("User Not Found")
        );
        var course = Course.builder()
                .title(courseDTO.getTitle())
                .description(courseDTO.getDescription())
                .category(courseDTO.getCategory())
                .duration(courseDTO.getDuration())
                .skillLevel(courseDTO.getSkillLevel())
                .published(false)
                .publishedBy(user.getId())
                .build();

        return modelMapper.map(courseRepository.save(course), CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Course Not Found With : "+id));
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory());
        course.setDuration(dto.getDuration());
        course.setSkillLevel(dto.getSkillLevel());
        Course updatedCourse = courseRepository.save(course);
        return modelMapper.map(updatedCourse, CourseDTO.class);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> allCourses = courseRepository.findAll();
        return allCourses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .toList();
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Course Not Found"));
        return modelMapper.map(course, CourseDTO.class);

    }

    @Override
    public CourseDTO togglePublished(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Course Not Found"));
        course.setPublished(!course.isPublished());
        Course updatedCourse = courseRepository.save(course);
        return modelMapper.map(updatedCourse, CourseDTO.class);
    }

    @Override
    public boolean deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if(course.isPresent()){
            courseRepository.delete(course.get());
            return true;
        }
        throw new ResourceNotFound("Course Not Found...");
    }

    @Override
    public List<CourseDTO> getCoursesByPublishedBy(Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return courseRepository.findByPublishedBy(userId)
                    .stream()
                    .map(course -> modelMapper.map(course,CourseDTO.class))
                    .toList();

        }
        throw new UserNotFoundError("User Not Found");
    }
}
