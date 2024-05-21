package com.emetaplus.admin.workplace.repository;

import com.emetaplus.admin.workplace.model.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface WorkplaceRepository extends JpaRepository<Workplace, UUID>, JpaSpecificationExecutor<Workplace> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndId(String email, UUID id);

    Optional<Workplace> findOneByWorkplaceId(UUID workplaceId);
}
