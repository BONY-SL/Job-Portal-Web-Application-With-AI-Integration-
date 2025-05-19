package com.job.lk.webapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    private Long jobId;

    private Long applicantId;

    private String resumeUrl;

    private String applicationStatus;

    private LocalDate appliedDate;

    @PrePersist
    public void setAppliedDate() {
        if (this.appliedDate == null) {
            this.appliedDate = LocalDate.now();
        }
    }
}
