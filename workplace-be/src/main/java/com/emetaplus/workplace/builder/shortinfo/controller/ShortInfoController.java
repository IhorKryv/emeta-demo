package com.emetaplus.workplace.builder.shortinfo.controller;

import com.emetaplus.workplace.builder.shortinfo.dto.ShortInfoDTO;
import com.emetaplus.workplace.builder.shortinfo.mapper.ShortInfoMapper;
import com.emetaplus.workplace.builder.shortinfo.service.ShortInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile/short-info")
@RequiredArgsConstructor
public class ShortInfoController {

    private final ShortInfoService shortInfoService;
    private final ShortInfoMapper shortInfoMapper;

    @GetMapping("my")
    public ShortInfoDTO getMyShortInfo() {
        return shortInfoMapper.toDTO(shortInfoService.getShortInfo());
    }

    @PutMapping("my/title")
    public void saveTitle(@RequestBody String title) {
        shortInfoService.saveShortInfoTitle(title);
    }

    @PutMapping("my/description")
    public void saveDescription(@RequestBody String description) {
        shortInfoService.saveShortInfoDescription(description);
    }

    @PutMapping("my/card/{cardId}/icon")
    public void saveCardIcon(@PathVariable UUID cardId, @RequestBody String icon) {
        shortInfoService.saveShortInfoCardIcon(cardId, icon);
    }

    @PutMapping("my/card/{cardId}/title")
    public void saveCardTitle(@PathVariable UUID cardId, @RequestBody String title) {
        shortInfoService.saveShortInfoCardTitle(cardId, title);
    }

    @PutMapping("my/card/{cardId}/text")
    public void saveCardText(@PathVariable UUID cardId, @RequestBody String text) {
        shortInfoService.saveShortInfoCardText(cardId, text);
    }
}
