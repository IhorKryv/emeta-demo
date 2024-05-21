package com.emetaplus.admin.role.controller;

import com.emetaplus.admin.role.dto.AuthorityDTO;
import com.emetaplus.admin.role.model.Authority;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/authority")
@CrossOrigin
public class AuthorityController {

    private static final String GET_ALL_PATH = "get/all";

    @GetMapping(GET_ALL_PATH)
    @PreAuthorize("hasAuthority('ROLE_MANAGER_AUTHORITY')")
    public List<AuthorityDTO> getAllAuthorities() {
        return Arrays
                .stream(Authority.values())
                .map(a -> AuthorityDTO
                        .builder()
                        .name(a.name())
                        .description(a.getValue())
                        .build())
                .toList();
    }
}
