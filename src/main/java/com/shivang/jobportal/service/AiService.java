package com.shivang.jobportal.service;

import com.shivang.jobportal.dto.AiJobRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient.Builder chatClientBuilder;

    public String generateJobDescription(AiJobRequest request) {

        ChatClient chatClient = chatClientBuilder.build();

        String prompt = """
Generate a COMPLETE professional job description for the following role.

Role: %s
Skills: %s
Experience: %s

Requirements:
- Do NOT use placeholders
- Do NOT write template text
- Generate realistic company-style content
- Include:
  1. Job Summary
  2. Responsibilities
  3. Required Skills
  4. Qualifications
  5. Nice to Have Skills
  6. Benefits

Write it like a real company hiring post.
"""
                .formatted(
                        request.getRole(),
                        request.getSkills(),
                        request.getExperience()
                );

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}