package com.emetaplus.workplace.builder.decks.mapper;

import com.emetaplus.workplace.builder.decks.dto.ProfileDeckDTO;
import com.emetaplus.workplace.builder.decks.model.ProfileDeck;
import com.emetaplus.workplace.builder.decks.model.ProfileDeckItem;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.profile.service.ProfileService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ProfileDeckMapper extends AbstractMapperFactory {

    private final ProfileService profileService;

    protected ProfileDeckMapper(ModelMapper mapper, ProfileService profileService) {
        super(mapper);
        this.profileService = profileService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<ProfileDeck, ProfileDeckDTO> profileDeckInstance = new MappingInstance<>(ProfileDeck.class, ProfileDeckDTO.class);
        profileDeckInstance.setPostMappingToDto((profileDeck, profileDeckDTO) -> {
            profileDeckDTO.setProfileId(profileDeck.getProfile().getId());
            profileDeckDTO.getDecks().stream()
                    .filter(item -> StringUtils.isNotBlank(item.getImage()))
                    .forEach(item -> item.setImage(profileService.getImageUrl(profileDeck.getProfile().getId().toString(), item.getImage())));
            profileDeckDTO.setDecks(profileDeckDTO.getDecks().stream().sorted(Comparator.comparingInt(ProfileDeckItem::getOrderValue)).toList());
        });
        builder.add(profileDeckInstance);
    }

    public ProfileDeckDTO toDTO(ProfileDeck profileDeck) {
        return getMapper(ProfileDeck.class, ProfileDeckDTO.class).toDTO(profileDeck);
    }
}
