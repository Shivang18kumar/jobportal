package com.shivang.jobportal.dto;

import com.shivang.jobportal.enums.ApplicationStatus;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {

    private ApplicationStatus status;
}