package com.emetaplus.admin.board.repository;

import com.emetaplus.admin.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {

    Optional<Board> findOneByName(String name);
}
