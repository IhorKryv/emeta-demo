package com.emetaplus.workplace.builder.shortinfo.repository;

import com.emetaplus.workplace.builder.shortinfo.model.ShortInfoCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShortInfoCardRepository extends JpaRepository<ShortInfoCard, UUID> {
}
