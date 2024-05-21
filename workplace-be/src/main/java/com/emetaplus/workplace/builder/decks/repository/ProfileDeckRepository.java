package com.emetaplus.workplace.builder.decks.repository;

import com.emetaplus.workplace.builder.decks.model.ProfileDeck;
import com.emetaplus.workplace.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileDeckRepository extends JpaRepository<ProfileDeck, UUID> {
    Optional<ProfileDeck> findOneByProfile(Profile profile);
}
