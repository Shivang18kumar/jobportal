package com.shivang.jobportal.controller;

import com.shivang.jobportal.entity.Job;
import com.shivang.jobportal.entity.User;
import com.shivang.jobportal.repository.UserRepository;
import com.shivang.jobportal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final UserRepository userRepository;

    // ================= USER =================

    @GetMapping("/user/jobs")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // ================= RECRUITER =================

    @PostMapping("/recruiter/jobs")
    public Job addJob(@Valid @RequestBody Job job, Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobService.addJob(job, user.getId());
    }

    @GetMapping("/recruiter/jobs")
    public List<Job> getRecruiterJobs(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobService.getJobsByRecruiter(user.getId());
    }

    // 🔥 UPDATE with ownership check
    @PutMapping("/recruiter/jobs/{id}")
    public Job updateJob(@PathVariable Long id,
                         @Valid @RequestBody Job job,
                         Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobService.updateJobWithOwnerCheck(id, job, user.getId());
    }

    // 🔥 DELETE with ownership check
    @DeleteMapping("/recruiter/jobs/{id}")
    public String deleteJob(@PathVariable Long id, Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        jobService.deleteJobWithOwnerCheck(id, user.getId());

        return "Job deleted successfully";
    }
}