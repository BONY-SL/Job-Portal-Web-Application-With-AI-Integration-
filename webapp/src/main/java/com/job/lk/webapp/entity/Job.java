package com.job.lk.webapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long companyId;
    private String location;
    private String description;
    private String skillsRequired;
    private String experienceRequired;
    private String industry;
    private String salary;
    private boolean active;
    private LocalDate publishDate;

    @PrePersist
    public void setAppliedDate() {
        if (this.publishDate == null) {
            this.publishDate = LocalDate.now();
        }
    }
}
