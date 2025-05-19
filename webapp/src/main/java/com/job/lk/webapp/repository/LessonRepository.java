package com.job.lk.webapp.repository;

import com.job.lk.webapp.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Long> {

    List<Lesson> findByModuleId(Long moduleId);
}
