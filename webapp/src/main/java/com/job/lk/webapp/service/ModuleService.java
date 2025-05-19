package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.ModuleDTO;

import java.util.List;

public interface ModuleService {
    ModuleDTO createModule(ModuleDTO dto);

    List<ModuleDTO> getModulesByCourse(Long courseId);

    ModuleDTO updateModule(Long id, ModuleDTO dto);

    boolean deleteModule(Long id);
}
