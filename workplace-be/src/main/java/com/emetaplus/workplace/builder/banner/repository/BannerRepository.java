package com.emetaplus.workplace.builder.banner.repository;

import com.emetaplus.workplace.builder.banner.model.Banner;
import com.emetaplus.workplace.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BannerRepository extends JpaRepository<Banner, UUID> {
    Optional<Banner> findOneByProfile(Profile profile);
}
