package com.emetaplus.workplace.profile.repository;

import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findOneByUser(User user);

    Optional<Profile> findOneByUrl(String url);
}
