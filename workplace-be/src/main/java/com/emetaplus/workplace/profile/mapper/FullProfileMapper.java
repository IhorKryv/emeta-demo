package com.emetaplus.workplace.profile.mapper;

import com.emetaplus.workplace.builder.banner.mapper.BannerMapper;
import com.emetaplus.workplace.builder.contact.mapper.ProfileContactMapper;
import com.emetaplus.workplace.builder.decks.mapper.ProfileDeckMapper;
import com.emetaplus.workplace.builder.schedule.mapper.ScheduleMapper;
import com.emetaplus.workplace.builder.shortinfo.mapper.ShortInfoMapper;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.profile.dto.FullProfileDTO;
import com.emetaplus.workplace.profile.model.FullProfile;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FullProfileMapper extends AbstractMapperFactory {

    private final BannerMapper bannerMapper;
    private final ShortInfoMapper shortInfoMapper;
    private final ScheduleMapper scheduleMapper;
    private final ProfileDeckMapper profileDeckMapper;
    private final ProfileContactMapper profileContactMapper;

    protected FullProfileMapper(ModelMapper mapper, BannerMapper bannerMapper, ShortInfoMapper shortInfoMapper, ScheduleMapper scheduleMapper, ProfileDeckMapper profileDeckMapper, ProfileContactMapper profileContactMapper) {
        super(mapper);
        this.bannerMapper = bannerMapper;
        this.shortInfoMapper = shortInfoMapper;
        this.scheduleMapper = scheduleMapper;
        this.profileDeckMapper = profileDeckMapper;
        this.profileContactMapper = profileContactMapper;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<FullProfile, FullProfileDTO> fullProfileInstance = new MappingInstance<>(FullProfile.class, FullProfileDTO.class);
        fullProfileInstance.setPostMappingToDto((fullProfile, fullProfileDTO) -> {
            fullProfileDTO.setBanner(bannerMapper.toDTO(fullProfile.getBanner()));
            fullProfileDTO.setSchedule(scheduleMapper.toDTO(fullProfile.getSchedule()));
            fullProfileDTO.setShortInfo(shortInfoMapper.toDTO(fullProfile.getShortInfo()));
            fullProfileDTO.setProfileDeck(profileDeckMapper.toDTO(fullProfile.getProfileDeck()));
            fullProfileDTO.setProfileContact(profileContactMapper.toDTO(fullProfile.getProfileContact()));
        });
        builder.add(fullProfileInstance);
    }

    public FullProfileDTO toDTO(FullProfile fullProfile) {
        return getMapper(FullProfile.class, FullProfileDTO.class).toDTO(fullProfile);
    }
}
