package com.emetaplus.workplace.userdeck.repository;

import com.emetaplus.workplace.userdeck.model.AdminDeck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdminDeckRepository extends JpaRepository<AdminDeck, UUID> {
    List<AdminDeck> findAllByWorkplaceId(UUID workplaceId);

    boolean existsAdminDeckByWorkplaceIdAndAdminId(UUID workplaceId, UUID adminId);
}
