package com.emetaplus.workplace.profile.service;

import com.emetaplus.workplace.builder.banner.repository.BannerRepository;
import com.emetaplus.workplace.builder.banner.service.BannerService;
import com.emetaplus.workplace.builder.contact.repository.ProfileContactRepository;
import com.emetaplus.workplace.builder.decks.repository.ProfileDeckRepository;
import com.emetaplus.workplace.builder.decks.service.ProfileDeckService;
import com.emetaplus.workplace.builder.schedule.model.Schedule;
import com.emetaplus.workplace.builder.schedule.repository.ScheduleRepository;
import com.emetaplus.workplace.builder.schedule.service.ScheduleService;
import com.emetaplus.workplace.builder.shortinfo.repository.ShortInfoRepository;
import com.emetaplus.workplace.builder.shortinfo.service.ShortInfoService;
import com.emetaplus.workplace.profile.model.FullProfile;
import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfilePublicService {
    private final BannerRepository bannerRepository;
    private final ShortInfoRepository shortInfoRepository;
    private final ScheduleRepository scheduleRepository;
    private final ProfileDeckRepository profileDeckRepository;
    private final ProfileRepository profileRepository;
    private final ProfileContactRepository profileContactRepository;

    public FullProfile getFullProfile(UUID profileId) {
        Profile profile = profileRepository.findById(profileId).orElse(null);
        if (Objects.isNull(profile)) {
            return null;
        }
        FullProfile fullProfile = new FullProfile();
        fullProfile.setProfileId(profile.getId());
        fullProfile.setProfileURL(profile.getUrl());
        fullProfile.setBanner(bannerRepository.findOneByProfile(profile).orElse(null));
        fullProfile.setShortInfo(shortInfoRepository.findOneByProfile(profile).orElse(null));
        fullProfile.setSchedule(scheduleRepository.findOneByProfile(profile).orElse(null));
        fullProfile.setProfileDeck(profileDeckRepository.findOneByProfile(profile).orElse(null));
        fullProfile.setProfileContact(profileContactRepository.findOneByProfile(profile).orElse(null));
        return fullProfile;
    }
}
