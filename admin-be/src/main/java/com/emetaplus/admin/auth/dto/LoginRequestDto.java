package com.emetaplus.admin.auth.dto;


import com.emetaplus.admin.utils.ValidationRules;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginRequestDto {

    @NotNull
    @Size(min = ValidationRules.USER_NAME_LENGTH)
    @Pattern(regexp = ValidationRules.USER_NAME_REGEXP)
    private String username;

    @NotNull
    @Size(min = ValidationRules.PASSWORD_LENGTH)
    @Pattern(regexp = ValidationRules.PASSWORD_REGEXP)
    private String password;
}
