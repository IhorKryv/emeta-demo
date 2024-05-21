package com.emetaplus.workplace.userworkplace.repository;

import com.emetaplus.workplace.userworkplace.model.UserWorkplace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserWorkplaceRepository extends JpaRepository<UserWorkplace, UUID> {
}
