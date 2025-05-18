package com.job.lk.webapp.repository;

import com.job.lk.webapp.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
}
