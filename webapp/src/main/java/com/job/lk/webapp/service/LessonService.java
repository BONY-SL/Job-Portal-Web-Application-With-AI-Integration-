package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.LessonDTO;

import java.util.List;

public interface LessonService {
    LessonDTO createLesson(LessonDTO lessonDTO);

    List<LessonDTO> getLessonsByModule(Long moduleId);

    LessonDTO updateLessonCompletionStatus(Long lessonId, Boolean isCompleted);

    LessonDTO updateLesson(LessonDTO lessonDTO);

    boolean deleteLesson(Long lessonId);
}
