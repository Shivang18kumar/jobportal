package com.shivang.jobportal.entity;

import com.shivang.jobportal.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long jobId;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}