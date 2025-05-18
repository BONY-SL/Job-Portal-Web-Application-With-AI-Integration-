package com.job.lk.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String duration;
    private String skillLevel;
    private boolean published;
    private Long publishedBy;
}
