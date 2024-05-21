package com.emetaplus.admin.deck.repository;

import com.emetaplus.admin.deck.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeckRepository extends JpaRepository<Deck, UUID>, JpaSpecificationExecutor<Deck> {
    Optional<Deck> findOneByName(String name);

    List<Deck> findAllByIdIn(List<UUID> ids);

    List<Deck> findAllByAvailableTrue();
}
