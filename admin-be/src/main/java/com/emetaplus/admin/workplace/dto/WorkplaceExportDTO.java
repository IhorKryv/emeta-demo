package com.emetaplus.admin.workplace.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WorkplaceExportDTO {
    private UUID workplaceId;
    private String email;
    private String temporaryPassword;
    private String firstName;
    private String lastName;
    private String phone;
}
