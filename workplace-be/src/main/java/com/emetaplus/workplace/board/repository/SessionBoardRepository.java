package com.emetaplus.workplace.board.repository;

import com.emetaplus.workplace.board.model.SessionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionBoardRepository extends JpaRepository<SessionBoard, UUID>, JpaSpecificationExecutor<SessionBoard> {
    Optional<SessionBoard> findOneByName(String name);

    List<SessionBoard> findAllByWorkplaceId(UUID workplaceId);
}
