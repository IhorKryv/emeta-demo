package com.emetaplus.workplace.builder.decks.controller;

import com.emetaplus.workplace.builder.decks.dto.ProfileDeckDTO;
import com.emetaplus.workplace.builder.decks.mapper.ProfileDeckMapper;
import com.emetaplus.workplace.builder.decks.model.ProfileDeckItem;
import com.emetaplus.workplace.builder.decks.service.ProfileDeckService;
import com.emetaplus.workplace.profile.model.ProfileImage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile/deck")
@RequiredArgsConstructor
public class ProfileDeckController {

    private final ProfileDeckService profileDeckService;
    private final ProfileDeckMapper profileDeckMapper;

    @GetMapping("my")
    public ProfileDeckDTO getMyDeck() {
        return profileDeckMapper.toDTO(profileDeckService.getProfileDeck());
    }

    @GetMapping("my/add")
    public ProfileDeckItem addDeckItem() {
        return profileDeckService.addDeckToSection();
    }

    @PutMapping("my/title")
    public void saveTitle(@RequestBody String title) {
        profileDeckService.saveTitle(title);
    }

    @PutMapping("my/info")
    public void saveInfo(@RequestBody String info) {
        profileDeckService.saveInfo(info);
    }

    @PutMapping("my/{id}/image")
    public ProfileImage saveDeckItemImage(@PathVariable UUID id, @RequestParam("image") MultipartFile file) {
        return new ProfileImage(profileDeckService.saveDeckImage(id, file));
    }

    @PutMapping("my/{id}/title")
    public void saveDeckItemTitle(@PathVariable UUID id, @RequestBody String title) {
        profileDeckService.saveDeckTitle(id, title);
    }

    @PutMapping("my/{id}/info")
    public void saveDeckItemInfo(@PathVariable UUID id, @RequestBody String info) {
        profileDeckService.saveDeckInfo(id, info);
    }

    @DeleteMapping("my/remove/{id}")
    public void removeDeckItem(@PathVariable UUID id) {
        profileDeckService.removeDeckFromSection(id);
    }
}
