package com.emetaplus.workplace.builder.contact.service;

import com.emetaplus.workplace.builder.contact.model.ProfileContact;
import com.emetaplus.workplace.builder.contact.repository.ProfileContactRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileContactService {
    private final ProfileContactRepository profileContactRepository;
    private final ProfileService profileService;

    public ProfileContact getProfileContact() {
        Profile profile = profileService.getMyProfile();
        return profileContactRepository.findOneByProfile(profile).orElseGet(this::initProfileContact);
    }

    public void saveTitle(String title) {
        ProfileContact profileContact = getProfileContact();
        profileContact.setTitle(title);
        profileContactRepository.save(profileContact);
    }

    public void saveShortText(String shortText) {
        ProfileContact profileContact = getProfileContact();
        profileContact.setShortText(shortText);
        profileContactRepository.save(profileContact);
    }

    public void saveEmail(String email) {
        ProfileContact profileContact = getProfileContact();
        profileContact.setEmail(email);
        profileContactRepository.save(profileContact);
    }

    public void savePhone(String phone) {
        ProfileContact profileContact = getProfileContact();
        profileContact.setPhone(phone);
        profileContactRepository.save(profileContact);
    }

    private ProfileContact getMyProfileContact() {
        Profile profile = profileService.getMyProfile();
        return profileContactRepository.findOneByProfile(profile)
                .orElseThrow(() -> ExceptionHelper.getNotFoundException(ProfileContact.class));
    }

    private ProfileContact initProfileContact() {
        Profile profile = profileService.getMyProfile();
        ProfileContact profileContact = new ProfileContact();
        profileContact.setProfile(profile);
        return profileContactRepository.save(profileContact);
    }

}
