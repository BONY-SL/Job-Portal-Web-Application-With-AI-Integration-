package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.CourseDTO;
import com.job.lk.webapp.service.CourseService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<JsonResponse> createCourse(@RequestBody CourseDTO courseDTO){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Course Created Successfully...",
                courseService.createCourse(courseDTO)
        ),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponse> updateCourse(@PathVariable (value = "id") Long id, @RequestBody CourseDTO dto) {

        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Course Update Successfully...",
                courseService.updateCourse(id, dto)
        ),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getCourses() {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                courseService.getAllCourses()
        ),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResponse> getCourseById(@PathVariable (value = "id") Long id) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                courseService.getCourseById(id)
        ),HttpStatus.OK);
    }


    @PatchMapping("/{id}/toggle")
    public ResponseEntity<JsonResponse> togglePublish(@PathVariable (value = "id")  Long id) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                courseService.togglePublished(id)
        ),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponse> deleteCourse(@PathVariable (value = "id") Long id) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Course Deleted Successfully...",
                courseService.deleteCourse(id)
        ),HttpStatus.OK);
    }

    @GetMapping("/published-by/{userId}")
    public ResponseEntity<JsonResponse> getCoursesByPublishedBy(@PathVariable (value = "userId") Long userId) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                courseService.getCoursesByPublishedBy(userId)
        ),HttpStatus.OK);
    }
}
