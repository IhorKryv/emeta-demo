package com.emetaplus.workplace.builder.banner.service;

import com.emetaplus.workplace.builder.banner.model.Banner;
import com.emetaplus.workplace.builder.banner.repository.BannerRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;
    private final ProfileService profileService;
    private final MinioService minioService;

    public void saveTitle(String title) {
        Banner banner = getMyBanner();
        banner.setTitle(title);
        bannerRepository.save(banner);
    }

    public void saveInfo(String info) {
        Banner banner = getMyBanner();
        banner.setInfo(info);
        bannerRepository.save(banner);
    }

    public String saveImage(MultipartFile file) {
        Banner banner = getMyBanner();
        String profileId = banner.getProfile().getId().toString();
        if (StringUtils.isNotBlank(banner.getImage())) {
            removeImage(profileId, banner.getImage());
        }
        String imageName = new Date().getTime() + "-" + file.getOriginalFilename();
        banner.setImage(imageName);
        bannerRepository.save(banner);
        uploadImage(profileId, imageName, file);
        return profileService.getImageUrl(profileId, imageName);
    }

    public String saveBackground(MultipartFile file) {
        Banner banner = getMyBanner();
        String profileId = banner.getProfile().getId().toString();
        if (StringUtils.isNotBlank(banner.getBackground())) {
            removeImage(profileId, banner.getBackground());
        }
        String imageName = new Date().getTime() + "-" + file.getOriginalFilename();
        banner.setBackground(imageName);
        bannerRepository.save(banner);
        uploadImage(profileId, imageName, file);
        return profileService.getImageUrl(profileId, imageName);
    }

    public Banner getBanner() {
        Profile profile = profileService.getMyProfile();
        return bannerRepository.findOneByProfile(profile).orElseGet(this::initBanner);
    }

    private Banner initBanner() {
        Profile profile = profileService.getMyProfile();
        Banner banner = new Banner();
        banner.setProfile(profile);
        return bannerRepository.save(banner);
    }

    private void uploadImage(String profileId, String filename, MultipartFile file) {
        try {
            minioService.addImageToProfile(
                    profileId,
                    filename,
                    new ByteArrayInputStream(file.getBytes()),
                    file.getSize());
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }
    }

    private void removeImage(String profileId, String filename) {
        minioService.removeImageFromProfile(profileId, filename);
    }

    private Banner getMyBanner() {
        Profile profile = profileService.getMyProfile();
        return bannerRepository.findOneByProfile(profile).orElseThrow(() -> ExceptionHelper.getNotFoundException(Banner.class));
    }
}
