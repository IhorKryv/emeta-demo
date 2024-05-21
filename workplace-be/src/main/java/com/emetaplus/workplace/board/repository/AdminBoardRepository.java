package com.emetaplus.workplace.board.repository;

import com.emetaplus.workplace.board.model.AdminBoard;
import com.emetaplus.workplace.userdeck.model.AdminDeck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdminBoardRepository extends JpaRepository<AdminBoard, UUID> {
    List<AdminBoard> findAllByWorkplaceId(UUID workplaceId);

    boolean existsAdminBoardByWorkplaceIdAndAdminId(UUID workplaceId, UUID adminId);
}
