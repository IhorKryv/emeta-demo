package com.emetaplus.workplace.builder.shortinfo.service;

import com.emetaplus.workplace.builder.shortinfo.model.ShortInfo;
import com.emetaplus.workplace.builder.shortinfo.model.ShortInfoCard;
import com.emetaplus.workplace.builder.shortinfo.repository.ShortInfoCardRepository;
import com.emetaplus.workplace.builder.shortinfo.repository.ShortInfoRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShortInfoService {

    private final ShortInfoRepository shortInfoRepository;
    private final ShortInfoCardRepository shortInfoCardRepository;
    private final ProfileService profileService;

    public ShortInfo getShortInfo() {
        Profile profile = profileService.getMyProfile();
        ShortInfo info = shortInfoRepository.findOneByProfile(profile).orElseGet(this::initShortInfo);
        info.setCards(info.getCards());
        return info;
    }

    public void saveShortInfoTitle(String title) {
        ShortInfo shortInfo = getMyShortInfo();
        shortInfo.setTitle(title);
        shortInfoRepository.save(shortInfo);
    }

    public void saveShortInfoDescription(String description) {
        ShortInfo shortInfo = getMyShortInfo();
        shortInfo.setDescription(description);
        shortInfoRepository.save(shortInfo);
    }

    public void saveShortInfoCardIcon(UUID cardId, String icon) {
        ShortInfo shortInfo = getMyShortInfo();
        ShortInfoCard card = shortInfo.getCards().stream().filter(c -> c.getId().equals(cardId)).findFirst()
                .orElseThrow(() -> ExceptionHelper.getNotFoundException(ShortInfoCard.class));
        card.setIcon(icon);
        shortInfoCardRepository.save(card);
    }

    public void saveShortInfoCardTitle(UUID cardId, String title) {
        ShortInfo shortInfo = getMyShortInfo();
        ShortInfoCard card = shortInfo.getCards().stream().filter(c -> c.getId().equals(cardId)).findFirst()
                .orElseThrow(() -> ExceptionHelper.getNotFoundException(ShortInfoCard.class));
        card.setTitle(title);
        shortInfoCardRepository.save(card);
    }

    public void saveShortInfoCardText(UUID cardId, String text) {
        ShortInfo shortInfo = getMyShortInfo();
        ShortInfoCard card = shortInfo.getCards().stream().filter(c -> c.getId().equals(cardId)).findFirst()
                .orElseThrow(() -> ExceptionHelper.getNotFoundException(ShortInfoCard.class));
        card.setText(text);
        shortInfoCardRepository.save(card);
    }

    private ShortInfo initShortInfo() {
        Profile profile = profileService.getMyProfile();
        List<ShortInfoCard> cards = new ArrayList<>();
        ShortInfo shortInfo = new ShortInfo();
        shortInfo.setProfile(profile);
        shortInfo = shortInfoRepository.save(shortInfo);
        initShortInfoCards(shortInfo.getId(), cards);
        shortInfo.setCards(cards);
        return shortInfoRepository.save(shortInfo);
    }

    private void initShortInfoCards(UUID shortInfoId, List<ShortInfoCard> cards) {
        for (int i = 0; i < 3; i++) {
            ShortInfoCard card = new ShortInfoCard();
            card.setShortInfoId(shortInfoId);
            card.setOrderValue(i);
            cards.add(card);
        }
        shortInfoCardRepository.saveAll(cards);
    }

    private ShortInfo getMyShortInfo() {
        Profile profile = profileService.getMyProfile();
        return shortInfoRepository.findOneByProfile(profile).orElseThrow(() -> ExceptionHelper.getNotFoundException(ShortInfo.class));
    }
}
