package com.job.lk.webapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDTO {

    private Long id;
    private Long userId;
    private String name;
    private String location;
}
