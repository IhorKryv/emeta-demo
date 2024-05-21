package com.emetaplus.admin.role.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoleDTO {
    private UUID id;
    private String name;
    private String description;
    private List<String> authorities;
}
