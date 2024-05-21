package com.emetaplus.workplace.profile.service;

import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.profile.repository.ProfileRepository;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MinioService minioService;

    public UUID createProfile() {
        User user = UserUtils.getCurrentUser();
        Profile profile = new Profile();
        profile.setUrl(user.getId().toString());
        profile.setUser(user);
        profile = profileRepository.save(profile);
        minioService.createProfileStorage(profile.getId().toString());
        return profile.getId();
    }

    public void saveProfileURL(String value) {
        String url = value.trim();
        profileRepository.findOneByUrl(url).map(existing -> {
            Profile myProfile = getMyProfile();
            if (!myProfile.getId().equals(existing.getId())) {
                throw ExceptionHelper.getAlreadyExistsException(Profile.class, "url");
            }
            existing.setUrl(url);
            return profileRepository.save(existing);
        }).orElseGet(() -> {
            Profile myProfile = getMyProfile();
            myProfile.setUrl(url);
            return profileRepository.save(myProfile);
        });
    }

    public Profile getMyProfile() {
        User user = UserUtils.getCurrentUser();
        return profileRepository.findOneByUser(user)
                .orElseThrow(() -> ExceptionHelper.getNotFoundException(Profile.class));
    }

    public String getImageUrl(String profileId, String name) {
        return minioService.getProfileImage(profileId, name);
    }
}
