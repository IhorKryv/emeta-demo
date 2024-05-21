package com.emetaplus.workplace.client.repository;

import com.emetaplus.workplace.client.model.ClientFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientFileRepository extends JpaRepository<ClientFile, UUID> {
}
