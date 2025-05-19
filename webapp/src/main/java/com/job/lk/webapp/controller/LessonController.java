package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.LessonDTO;
import com.job.lk.webapp.service.FileUploadService;
import com.job.lk.webapp.service.LessonService;
import com.job.lk.webapp.service.VideoUploadService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    private final FileUploadService fileUploadService;

    private final VideoUploadService videoUploadService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonResponse> createLesson(@RequestPart("title") String title,
                                                  @RequestPart("content") String content,
                                                  @RequestPart("moduleId") Long moduleId,
                                                  @RequestPart("video") MultipartFile video,
                                                  @RequestPart("file") MultipartFile file) throws IOException {
        String videoUrl = videoUploadService.uploadFile(video);
        String fileUrl = fileUploadService.uploadFile(file);

        var lessonDto = LessonDTO.builder().title(title).content(content)
                .moduleId(moduleId).videoUrl(videoUrl).fileUrl(fileUrl).isCompleted(false)
                .build();

        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Lesson Create Successfully",
                lessonService.createLesson(lessonDto)
        ),HttpStatus.CREATED);
    }


    @GetMapping("/by-module/{moduleId}")
    public ResponseEntity<JsonResponse> getLessonsByModule(@PathVariable Long moduleId) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                lessonService.getLessonsByModule(moduleId)
        ),HttpStatus.OK);
    }

    @PatchMapping("/{lessonId}/completion")
    public ResponseEntity<JsonResponse> updateLessonCompletion(@PathVariable Long lessonId,
                                                               @RequestParam("isCompleted") Boolean isCompleted) {
        LessonDTO updatedLesson = lessonService.updateLessonCompletionStatus(lessonId, isCompleted);

        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Lesson Completion Status Update Successfully",
                updatedLesson
        ),HttpStatus.CREATED);
    }

    @PutMapping(value = "/{lessonId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonResponse> updateLesson(@PathVariable Long lessonId,
                                                  @RequestPart("title") String title,
                                                  @RequestPart("content") String content,
                                                  @RequestPart("moduleId") Long moduleId,
                                                  @RequestPart(value = "video", required = false) MultipartFile video,
                                                  @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        String videoUrl = video != null ? videoUploadService.uploadFile(video) : null;
        String fileUrl = file != null ? fileUploadService.uploadFile(file) : null;

        var lessonDto = LessonDTO.builder().id(lessonId).title(title).content(content)
                .moduleId(moduleId).videoUrl(videoUrl).fileUrl(fileUrl)
                .build();

        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Lesson Update Successfully",
                lessonService.updateLesson(lessonDto)
        ),HttpStatus.CREATED);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<JsonResponse> deleteLesson(@PathVariable Long lessonId) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                lessonService.deleteLesson(lessonId)
        ),HttpStatus.OK);
    }


}
