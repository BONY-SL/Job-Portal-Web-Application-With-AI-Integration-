package com.job.lk.webapp.repository;

import com.job.lk.webapp.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module,Long> {

    List<Module> findByCourseId(Long courseId);
}
