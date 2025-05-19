package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.ResumeDTO;
import com.job.lk.webapp.service.FileUploadService;
import com.job.lk.webapp.service.ResumeService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    private final FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<JsonResponse> uploadResume(@RequestPart(value = "file")MultipartFile file,
                                                     @RequestPart(value = "userId") Long userId){

        String fileUrl = fileUploadService.uploadFile(file);

        var resumeDto = ResumeDTO.builder()
                .userId(userId)
                .resumeUrl(fileUrl).build();

        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Resume Upload SuccessFully",
                resumeService.saveResume(resumeDto)
        ),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResponse> getResumeById(@PathVariable Long id){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                resumeService.getResumeById(id)
        ),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponse> deleteResumeById(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Resume Delete Success",
                resumeService.deleteResumeById(id)
        ),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponse> updateResume(@PathVariable(value = "id") Long id,
                                                     @RequestParam (value = "file", required = false) MultipartFile file,
                                                     @RequestParam (value = "resumeUrl",required = false) String resumeUrl){

        ResumeDTO updatedResume = resumeService.updateResume(id, file, resumeUrl);
        JsonResponse response = new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Resume updated successfully",
                updatedResume
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ResumeDTO>>getResumesByUser(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.getResumesByUserId(id));
    }

}
