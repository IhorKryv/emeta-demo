package com.emetaplus.workplace.builder.contact.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProfileContactDTO {
    private UUID id;
    private UUID profileId;
    private String title;
    private String shortText;
    private String email;
    private String phone;
}
