package com.job.lk.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDTO {
    private Long id;
    private Long userId;
    private String resumeUrl;
}
