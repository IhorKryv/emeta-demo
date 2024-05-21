package com.emetaplus.admin.workplace.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkplaceUpdateDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    private String phone;
}
