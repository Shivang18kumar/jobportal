package com.shivang.jobportal.service;

import com.shivang.jobportal.entity.Job;
import com.shivang.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    // 🔹 Add Job
    public Job addJob(Job job, Long recruiterId) {
        job.setRecruiterId(recruiterId);
        return jobRepository.save(job);
    }

    // 🔹 Get All Jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // 🔹 Get Jobs by Recruiter
    public List<Job> getJobsByRecruiter(Long recruiterId) {
        return jobRepository.findByRecruiterId(recruiterId);
    }

    // 🔥 Update with ownership check
    public Job updateJobWithOwnerCheck(Long id, Job updatedJob, Long recruiterId) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 🔥 ownership validation
        if (!job.getRecruiterId().equals(recruiterId)) {
            throw new RuntimeException("You are not allowed to update this job");
        }

        job.setTitle(updatedJob.getTitle());
        job.setDescription(updatedJob.getDescription());
        job.setLocation(updatedJob.getLocation());
        job.setSalary(updatedJob.getSalary());

        return jobRepository.save(job);
    }

    // 🔥 Delete with ownership check
    public void deleteJobWithOwnerCheck(Long id, Long recruiterId) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 🔥 ownership validation
        if (!job.getRecruiterId().equals(recruiterId)) {
            throw new RuntimeException("You are not allowed to delete this job");
        }

        jobRepository.delete(job);
    }
}