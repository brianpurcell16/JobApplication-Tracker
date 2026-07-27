package com.jobtracker.dto;

import com.jobtracker.model.Application;
import com.jobtracker.model.ApplicationStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ApplicationRequest {
    private String company;
    private String role;
    private String location;
    private String jobUrl;
    private String jobDescription;
    private String notes;
    private ApplicationStatus status;
    private LocalDate dateApplied;
}
