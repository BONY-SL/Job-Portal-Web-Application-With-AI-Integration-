package com.job.lk.webapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDTO {
    private String name;
    private String email;
    private String profilePicture;
    private String password;
}
