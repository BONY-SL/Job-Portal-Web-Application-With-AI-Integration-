package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.ModuleDTO;
import com.job.lk.webapp.entity.Course;
import com.job.lk.webapp.entity.Module;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.repository.CourseRepository;
import com.job.lk.webapp.repository.ModuleRepository;
import com.job.lk.webapp.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public ModuleDTO createModule(ModuleDTO dto) {

        Course course = courseRepository.findById(dto.getCourseId()).orElseThrow(
                ()-> new ResourceNotFound("Course Not Found")
        );
        var module = Module.builder()
                .name(dto.getName())
                .course(course)
                .build();
        Module savedModule =  moduleRepository.save(module);
        return modelMapper.map(savedModule,ModuleDTO.class);
    }

    @Override
    public List<ModuleDTO> getModulesByCourse(Long courseId) {
        return moduleRepository.findByCourseId(courseId)
                .stream()
                .map(module -> modelMapper.map(module, ModuleDTO.class))
                .toList();
    }

    @Override
    public ModuleDTO updateModule(Long id, ModuleDTO dto) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Module not found"));

        module.setName(dto.getName());

        Module updated = moduleRepository.save(module);
        return new ModuleDTO(updated.getId(), updated.getName(), dto.getCourseId());
    }

    @Override
    public boolean deleteModule(Long id) {

        Optional<Module> module = moduleRepository.findById(id);
        if(module.isPresent()){
            moduleRepository.delete(module.get());
            return true;
        }
        throw new ResourceNotFound("Module Not Found..");

    }
}
