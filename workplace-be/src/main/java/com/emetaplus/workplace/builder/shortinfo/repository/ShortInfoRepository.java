package com.emetaplus.workplace.builder.shortinfo.repository;

import com.emetaplus.workplace.builder.shortinfo.model.ShortInfo;
import com.emetaplus.workplace.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShortInfoRepository extends JpaRepository<ShortInfo, UUID> {
    Optional<ShortInfo> findOneByProfile(Profile profile);
}
