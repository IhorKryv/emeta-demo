package com.emetaplus.workplace.profile.controller;

import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.profile.model.ProfileImage;
import com.emetaplus.workplace.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


    @PostMapping("save/url")
    public void saveProfileURL(@RequestBody String value) {
        profileService.saveProfileURL(value);
    }

    @GetMapping("create")
    public UUID createProfile() {
        return profileService.createProfile();
    }

    @GetMapping("get")
    public Profile getMyProfile() {
        return profileService.getMyProfile();
    }

    @GetMapping("/public/{profileId}/image/{name}")
    public ProfileImage getImageURL(@PathVariable String profileId, @PathVariable String name) {
        return new ProfileImage(profileService.getImageUrl(profileId, name));
    }
}
