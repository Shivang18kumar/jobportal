package com.shivang.jobportal.controller;

import com.shivang.jobportal.dto.UpdateApplicationStatusRequest;
import com.shivang.jobportal.entity.Application;
import com.shivang.jobportal.entity.User;
import com.shivang.jobportal.repository.UserRepository;
import com.shivang.jobportal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;

    // 🔹 Apply to job
    @PostMapping("/user/apply/{jobId}")
    public Application applyToJob(@PathVariable Long jobId, Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return applicationService.applyToJob(user.getId(), jobId);
    }

    // 🔹 USER: view own applications
    @GetMapping("/user/applications")
    public List<Application> getMyApplications(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return applicationService.getApplicationsByUser(user.getId());
    }

    // 🔹 RECRUITER: view applicants for a job
    @GetMapping("/recruiter/applications/{jobId}")
    public List<Application> getApplicationsForJob(@PathVariable Long jobId) {
        return applicationService.getApplicationsByJob(jobId);
    }
    @PutMapping("/recruiter/applications/{applicationId}/status")
    public Application updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody UpdateApplicationStatusRequest request) {

        return applicationService.updateApplicationStatus(
                applicationId,
                request.getStatus()
        );
    }
}