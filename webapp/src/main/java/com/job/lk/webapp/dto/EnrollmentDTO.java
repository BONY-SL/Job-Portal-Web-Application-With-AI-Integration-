package com.job.lk.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {
    private Long enrollmentId;
    private Long courseId;
    private Long userId;
    private LocalDate enrollmentDate;
    private Integer progressStatus;
}
