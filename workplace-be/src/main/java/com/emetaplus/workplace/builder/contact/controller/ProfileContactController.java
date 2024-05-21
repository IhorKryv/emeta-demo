package com.emetaplus.workplace.builder.contact.controller;

import com.emetaplus.workplace.builder.contact.dto.ProfileContactDTO;
import com.emetaplus.workplace.builder.contact.mapper.ProfileContactMapper;
import com.emetaplus.workplace.builder.contact.repository.ProfileContactRepository;
import com.emetaplus.workplace.builder.contact.service.ProfileContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/contact")
@RequiredArgsConstructor
public class ProfileContactController {

    private final ProfileContactService profileContactService;
    private final ProfileContactMapper profileContactMapper;

    @GetMapping ("my")
    public ProfileContactDTO getMyProfile() {
        return profileContactMapper.toDTO(profileContactService.getProfileContact());
    }

    @PutMapping("my/title")
    public void saveTitle(@RequestBody String title) {
        profileContactService.saveTitle(title);
    }

    @PutMapping("my/short-text")
    public void saveShortText(@RequestBody String shortText) {
        profileContactService.saveShortText(shortText);
    }

    @PutMapping("my/email")
    public void saveEmail(@RequestBody String email) {
        profileContactService.saveEmail(email);
    }

    @PutMapping("my/phone")
    public void savePhone(@RequestBody String phone) {
        profileContactService.savePhone(phone);
    }
}
