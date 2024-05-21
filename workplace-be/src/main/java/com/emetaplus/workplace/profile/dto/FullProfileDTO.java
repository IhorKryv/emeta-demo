package com.emetaplus.workplace.profile.dto;

import com.emetaplus.workplace.builder.banner.dto.BannerDTO;
import com.emetaplus.workplace.builder.banner.model.Banner;
import com.emetaplus.workplace.builder.contact.dto.ProfileContactDTO;
import com.emetaplus.workplace.builder.decks.dto.ProfileDeckDTO;
import com.emetaplus.workplace.builder.decks.model.ProfileDeck;
import com.emetaplus.workplace.builder.schedule.dto.ScheduleDTO;
import com.emetaplus.workplace.builder.schedule.model.Schedule;
import com.emetaplus.workplace.builder.shortinfo.dto.ShortInfoDTO;
import com.emetaplus.workplace.builder.shortinfo.model.ShortInfo;
import lombok.Data;

import java.util.UUID;

@Data
public class FullProfileDTO {
    private UUID profileId;
    private String profileURL;
    private BannerDTO banner;
    private ShortInfoDTO shortInfo;
    private ScheduleDTO schedule;
    private ProfileDeckDTO profileDeck;
    private ProfileContactDTO profileContact;
}
