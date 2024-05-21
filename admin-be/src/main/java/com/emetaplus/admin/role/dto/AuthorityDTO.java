package com.emetaplus.admin.role.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class AuthorityDTO {
    String name;
    String description;
}
