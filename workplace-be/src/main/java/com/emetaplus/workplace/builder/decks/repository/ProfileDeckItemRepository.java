package com.emetaplus.workplace.builder.decks.repository;

import com.emetaplus.workplace.builder.decks.model.ProfileDeckItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileDeckItemRepository extends JpaRepository<ProfileDeckItem, UUID> {
}
