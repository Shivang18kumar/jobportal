package com.shivang.jobportal.service;

import com.shivang.jobportal.entity.Application;
import com.shivang.jobportal.enums.ApplicationStatus;
import com.shivang.jobportal.repository.ApplicationRepository;
import com.shivang.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public Application applyToJob(Long userId, Long jobId) {

        // check job exists
        jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 🔥 prevent duplicate apply
        if (applicationRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new RuntimeException("You have already applied to this job");
        }

        Application application = Application.builder()
                .userId(userId)
                .jobId(jobId)
                .status(ApplicationStatus.APPLIED)
                .build();

        return applicationRepository.save(application);
    }
    // 🔹 Get applications by user
    public List<Application> getApplicationsByUser(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    // 🔹 Get applications by job (for recruiter)
    public List<Application> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }
    public Application updateApplicationStatus(Long applicationId,
                                               ApplicationStatus status) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(status);

        return applicationRepository.save(application);
    }
}