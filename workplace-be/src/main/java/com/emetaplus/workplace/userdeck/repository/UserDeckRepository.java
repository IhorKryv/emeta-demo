package com.emetaplus.workplace.userdeck.repository;

import com.emetaplus.workplace.userdeck.model.UserDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDeckRepository extends JpaRepository<UserDeck, UUID>, JpaSpecificationExecutor<UserDeck> {
    Optional<UserDeck> findOneByWorkplaceIdAndName(UUID workplaceId, String name);

    List<UserDeck> findAllByWorkplaceId(UUID workplaceId);

}
