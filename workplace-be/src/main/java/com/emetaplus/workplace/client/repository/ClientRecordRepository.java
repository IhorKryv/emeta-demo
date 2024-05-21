package com.emetaplus.workplace.client.repository;

import com.emetaplus.workplace.client.model.ClientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRecordRepository extends JpaRepository<ClientRecord, UUID> {
    List<ClientRecord> findAllByClientId(UUID clientId);
}
