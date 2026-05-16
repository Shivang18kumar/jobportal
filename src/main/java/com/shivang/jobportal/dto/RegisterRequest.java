package com.shivang.jobportal.dto;

import com.shivang.jobportal.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}