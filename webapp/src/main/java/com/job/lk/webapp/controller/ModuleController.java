package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.ModuleDTO;
import com.job.lk.webapp.service.ModuleService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    public ResponseEntity<JsonResponse> createModule(@RequestBody ModuleDTO dto) {

        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Module Created Success....",
                moduleService.createModule(dto)
        ),HttpStatus.CREATED);
    }

    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<JsonResponse> getModulesByCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                moduleService.getModulesByCourse(courseId)
        ),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponse> updateModule(@PathVariable Long id, @RequestBody ModuleDTO dto) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Module Update Success....",
                moduleService.updateModule(id, dto)
        ),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponse> deleteModule(@PathVariable Long id) {
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Module Deleted Success....",
                moduleService.deleteModule(id)
        ),HttpStatus.OK);
    }
}
