package com.emetaplus.workplace.builder.contact.repository;

import com.emetaplus.workplace.builder.contact.model.ProfileContact;
import com.emetaplus.workplace.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileContactRepository extends JpaRepository<ProfileContact, UUID> {
    Optional<ProfileContact> findOneByProfile(Profile profile);
}
