package com.emetaplus.workplace.builder.banner.controller;

import com.emetaplus.workplace.builder.banner.dto.BannerDTO;
import com.emetaplus.workplace.builder.banner.mapper.BannerMapper;
import com.emetaplus.workplace.builder.banner.service.BannerService;
import com.emetaplus.workplace.profile.model.ProfileImage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile/banner")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;
    private final BannerMapper bannerMapper;

    @GetMapping("my")
    public BannerDTO getMyBanner() {
        return bannerMapper.toDTO(bannerService.getBanner());
    }

    @PutMapping("my/title")
    public void saveTitle(@RequestBody String title) {
        bannerService.saveTitle(title);
    }

    @PutMapping("my/info")
    public void saveInfo(@RequestBody String info) {
        bannerService.saveInfo(info);
    }

    @PutMapping("my/image")
    public ProfileImage saveImage(@RequestParam(name = "image") MultipartFile file) {
        return new ProfileImage(bannerService.saveImage(file));
    }

    @PutMapping("my/background")
    public ProfileImage saveBackground(@RequestParam(name = "image") MultipartFile file) {
        return new ProfileImage(bannerService.saveBackground(file));
    }

}
