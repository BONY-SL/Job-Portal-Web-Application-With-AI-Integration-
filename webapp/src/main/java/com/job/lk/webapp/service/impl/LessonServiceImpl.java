package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.LessonDTO;
import com.job.lk.webapp.entity.Lesson;
import com.job.lk.webapp.entity.Module;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.repository.LessonRepository;
import com.job.lk.webapp.repository.ModuleRepository;
import com.job.lk.webapp.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepo;
    private final ModuleRepository moduleRepo;
    private final ModelMapper modelMapper;

    @Override
    public LessonDTO createLesson(LessonDTO lessonDTO) {

        Module module = moduleRepo.findById(lessonDTO.getModuleId()).orElseThrow(
                ()-> new ResourceNotFound("Module Not Found...")
        );

        var lesson = Lesson.builder().title(lessonDTO.getTitle()).content(lessonDTO.getContent())
                .videoUrl(lessonDTO.getVideoUrl()).fileUrl(lessonDTO.getFileUrl())
                .isCompleted(false).module(module)
                .build();
        Lesson savedLesson = lessonRepo.save(lesson);
        return modelMapper.map(savedLesson, LessonDTO.class);
    }

    @Override
    public List<LessonDTO> getLessonsByModule(Long moduleId) {
        return lessonRepo.findByModuleId(moduleId)
                .stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class))
                .toList();
    }

    @Override
    public LessonDTO updateLessonCompletionStatus(Long lessonId, Boolean isCompleted) {
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> new ResourceNotFound("Lesson not found"));
        lesson.setIsCompleted(isCompleted);
        Lesson updated = lessonRepo.save(lesson);
        return modelMapper.map(updated, LessonDTO.class);
    }

    @Override
    public LessonDTO updateLesson(LessonDTO lessonDTO) {

        Lesson lesson = lessonRepo.findById(lessonDTO.getId())
                .orElseThrow(() -> new ResourceNotFound("Lesson not found"));

        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());

        if (lessonDTO.getVideoUrl() != null) {
            lesson.setVideoUrl(lessonDTO.getVideoUrl());
        }
        if (lessonDTO.getFileUrl() != null) {
            lesson.setFileUrl(lessonDTO.getFileUrl());
        }
        Lesson savedLesson = lessonRepo.save(lesson);
        return modelMapper.map(savedLesson, LessonDTO.class);
    }

    @Override
    public boolean deleteLesson(Long lessonId) {
        Optional<Lesson> lessonOptional = lessonRepo.findById(lessonId);
        if(lessonOptional.isPresent()){
            lessonRepo.delete(lessonOptional.get());
            return true;
        }
        throw new ResourceNotFound("Lesson Not Found");
    }
}
