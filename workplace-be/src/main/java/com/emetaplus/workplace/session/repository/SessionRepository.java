package com.emetaplus.workplace.session.repository;

import com.emetaplus.workplace.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID>, JpaSpecificationExecutor<Session> {
    Optional<Session> findOneByNameAndWorkplaceId(String name, UUID workplaceId);

    List<Session> findAllByWorkplaceId(UUID workplaceId);
}
