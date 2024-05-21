package com.emetaplus.workplace.profile.controller;

import com.emetaplus.workplace.profile.dto.FullProfileDTO;
import com.emetaplus.workplace.profile.mapper.FullProfileMapper;
import com.emetaplus.workplace.profile.model.FullProfile;
import com.emetaplus.workplace.profile.service.ProfilePublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/public/profile")
@RequiredArgsConstructor
public class ProfilePublicController {
    private final ProfilePublicService profilePublicService;
    private final FullProfileMapper fullProfileMapper;

    @GetMapping("{profileId}")
    public FullProfileDTO getFullProfile(@PathVariable UUID profileId) {
        return fullProfileMapper.toDTO(profilePublicService.getFullProfile(profileId));
    }
}
