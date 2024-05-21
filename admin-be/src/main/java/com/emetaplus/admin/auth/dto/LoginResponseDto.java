package com.emetaplus.admin.auth.dto;

import com.emetaplus.admin.utils.ValidationRules;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class LoginResponseDto {

    @NotNull
    @Size(min = ValidationRules.USER_NAME_LENGTH)
    @Pattern(regexp = ValidationRules.USER_NAME_REGEXP)
    private String username;

    private List<String> authorities;

    private String token;
}
