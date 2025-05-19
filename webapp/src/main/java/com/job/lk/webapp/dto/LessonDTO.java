package com.job.lk.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDTO {
    private Long id;
    private String title;
    private String content;
    private String videoUrl;
    private String fileUrl;
    private Long moduleId;
    private Boolean isCompleted;
}
