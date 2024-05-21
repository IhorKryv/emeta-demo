package com.emetaplus.workplace.profile.model;

import com.emetaplus.workplace.builder.banner.model.Banner;
import com.emetaplus.workplace.builder.contact.model.ProfileContact;
import com.emetaplus.workplace.builder.decks.model.ProfileDeck;
import com.emetaplus.workplace.builder.schedule.model.Schedule;
import com.emetaplus.workplace.builder.shortinfo.model.ShortInfo;
import lombok.Data;

import java.util.UUID;

@Data
public class FullProfile {
    private UUID profileId;
    private String profileURL;
    private Banner banner;
    private ShortInfo shortInfo;
    private Schedule schedule;
    private ProfileDeck profileDeck;
    private ProfileContact profileContact;
}
