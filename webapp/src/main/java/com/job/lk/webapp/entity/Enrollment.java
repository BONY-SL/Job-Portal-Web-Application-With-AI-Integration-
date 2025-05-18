package com.job.lk.webapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    private Long courseId;
    private Long userId;
    private LocalDate enrollmentDate;
    private Integer progressStatus;

    @PrePersist
    public void setEnrollmentDate() {
        if (this.enrollmentDate == null) {
            this.enrollmentDate = LocalDate.now();
        }
    }
}
