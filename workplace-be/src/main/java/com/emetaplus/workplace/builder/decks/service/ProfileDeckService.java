package com.emetaplus.workplace.builder.decks.service;

import com.emetaplus.workplace.builder.decks.model.ProfileDeck;
import com.emetaplus.workplace.builder.decks.model.ProfileDeckItem;
import com.emetaplus.workplace.builder.decks.repository.ProfileDeckItemRepository;
import com.emetaplus.workplace.builder.decks.repository.ProfileDeckRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileDeckService {
    private final ProfileDeckItemRepository profileDeckItemRepository;
    private final ProfileDeckRepository profileDeckRepository;
    private final ProfileService profileService;
    private final MinioService minioService;

    public ProfileDeck getProfileDeck() {
        Profile profile = profileService.getMyProfile();
        return profileDeckRepository.findOneByProfile(profile).orElseGet(this::initProfileDeck);
    }

    public void saveTitle(String title) {
        ProfileDeck profileDeck = getMyProfileDeck();
        profileDeck.setTitle(title);
        profileDeckRepository.save(profileDeck);
    }

    public void saveInfo(String info) {
        ProfileDeck profileDeck = getMyProfileDeck();
        profileDeck.setInfo(info);
        profileDeckRepository.save(profileDeck);
    }

    public ProfileDeckItem addDeckToSection() {
        ProfileDeck profileDeck = getMyProfileDeck();
        ProfileDeckItem item = new ProfileDeckItem();
        item.setProfileDeckId(profileDeck.getId());
        item.setOrderValue(profileDeck.getDecks().size() + 1);
        return profileDeckItemRepository.save(item);
    }

    public void removeDeckFromSection(UUID deckId) {
        ProfileDeck profileDeck = getMyProfileDeck();
        if (profileDeck.getDecks().stream().anyMatch(d -> d.getId().equals(deckId))) {
            profileDeckItemRepository.deleteById(deckId);
        }
    }

    public String saveDeckImage(UUID deckId, MultipartFile file) {
        ProfileDeck profileDeck = getMyProfileDeck();
        ProfileDeckItem item = getDeckItem(deckId, profileDeck);
        if (StringUtils.isNotBlank(item.getImage())) {
            removeImage(profileDeck.getProfile().getId().toString(), item.getImage());
        }
        String filename = new Date().getTime() + "-" + file.getOriginalFilename();
        item.setImage(filename);
        profileDeckItemRepository.save(item);
        uploadImage(profileDeck.getProfile().getId().toString(), filename, file);
        return profileService.getImageUrl(profileDeck.getProfile().getId().toString(), filename);
    }

    public void saveDeckTitle(UUID deckId, String title) {
        ProfileDeck profileDeck = getMyProfileDeck();
        ProfileDeckItem item = getDeckItem(deckId, profileDeck);
        item.setTitle(title);
        profileDeckItemRepository.save(item);
    }

    public void saveDeckInfo(UUID deckId, String info) {
        ProfileDeck profileDeck = getMyProfileDeck();
        ProfileDeckItem item = getDeckItem(deckId, profileDeck);
        item.setInfo(info);
        profileDeckItemRepository.save(item);
    }

    private void uploadImage(String profileId, String filename, MultipartFile file) {
        try {
            minioService.addImageToProfile(
                    profileId,
                    filename,
                    new ByteArrayInputStream(file.getBytes()),
                    file.getSize());
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }
    }

    private void removeImage(String profileId, String filename) {
        minioService.removeImageFromProfile(profileId, filename);
    }

    private ProfileDeck initProfileDeck() {
        Profile profile = profileService.getMyProfile();
        ProfileDeck profileDeck = new ProfileDeck();
        profileDeck.setProfile(profile);
        profileDeck.setDecks(new ArrayList<>());
        return profileDeckRepository.save(profileDeck);
    }

    private ProfileDeck getMyProfileDeck() {
        Profile profile = profileService.getMyProfile();
        return profileDeckRepository.findOneByProfile(profile)
                .orElseThrow(() -> ExceptionHelper.getNotFoundException(ProfileDeck.class));
    }

    private ProfileDeckItem getDeckItem(UUID deckId, ProfileDeck profileDeck) {
        return profileDeck.getDecks().stream()
                .filter(d -> d.getId().equals(deckId))
                .findFirst()
                .orElseThrow(() -> ExceptionHelper.getNotFoundException(ProfileDeckItem.class));
    }
}
