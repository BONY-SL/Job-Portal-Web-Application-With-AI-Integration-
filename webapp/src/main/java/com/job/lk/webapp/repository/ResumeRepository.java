package com.job.lk.webapp.repository;

import com.job.lk.webapp.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findByUserId(Long userId);
}
