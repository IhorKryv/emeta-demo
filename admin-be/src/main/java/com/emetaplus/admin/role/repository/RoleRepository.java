package com.emetaplus.admin.role.repository;

import com.emetaplus.admin.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {
    Optional<Role> findOneByName(String name);

    List<Role> findAllByNameIn(List<String> names);

    @Query(value =
            "SELECT COUNT(*) FROM roles " +
                    "LEFT JOIN users_roles ur ON roles.role_id = ur.role_id " +
                    "WHERE roles.role_id = :id " +
                    "AND roles.role_id = ur.role_id", nativeQuery = true)
    Long checkIfRoleIsActive(UUID id);
}
