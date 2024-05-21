package com.emetaplus.admin.auth.dto;

import lombok.Data;

@Data
public class VerificationRequestDto {

    private String username;
    private String password;
    private String code;
}
