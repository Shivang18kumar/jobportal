package com.shivang.jobportal.controller;

import com.shivang.jobportal.dto.AiJobRequest;
import com.shivang.jobportal.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate-job-description")
    public String generateJobDescription(@RequestBody AiJobRequest request) {

        return aiService.generateJobDescription(request);
    }
}