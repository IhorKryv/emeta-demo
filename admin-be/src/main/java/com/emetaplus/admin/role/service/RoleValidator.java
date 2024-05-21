package com.emetaplus.admin.role.service;

import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.role.dto.RoleDTO;
import com.emetaplus.admin.role.model.Authority;
import com.emetaplus.admin.role.model.Role;
import com.emetaplus.admin.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleValidator {

    private final RoleRepository roleRepository;

    public void validateRoleForCreate(Role role) {
        validateName(null, role.getName(), "createNew");
        validateAuthorities(role.getAuthorities().stream().map(Authority::name).toList());
    }

    public void validateRoleForUpdate(UUID id, Role role) {
        validateName(id, role.getName(), "update");
        validateAuthorities(role.getAuthorities().stream().map(Authority::name).toList());
    }

    public void validateRoleForDelete(UUID id) {
        Long activeRoleUserRelationsCount = roleRepository.checkIfRoleIsActive(id);
        if (activeRoleUserRelationsCount > 0) {
            log.error("[Role Exception]: Role can't be deleted, because one or few users have this role");
            throw ExceptionHelper.getCannotDeleteException(Role.class);
        }
    }

    private void validateName(UUID id, String name, String method) {
        if (Objects.isNull(name) || name.isBlank()) {
            log.error("[Role Exception]: Can't {} role without name", method);
            throw ExceptionHelper.getInvalidFieldException(Role.class, "name");
        }

        if (isRoleWithNameAlreadyExists(id, name)) {
            log.error("[Role Exception]: Role with name={} already exists", name);
            throw ExceptionHelper.getAlreadyExistsException(Role.class, "name");
        }
    }

    private void validateAuthorities(List<String> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            log.error("[Role Exception]: Role must contain at least one authority");
            throw ExceptionHelper.getInvalidFieldException(Role.class, "authorities");
        }
        List<String> validAuthorities = Arrays.stream(Authority.values()).map(String::valueOf).toList();
        if (!(new HashSet<>(validAuthorities).containsAll(authorities))) {
            log.error("[Role Exception]: Role can't contain invalid authority");
            throw ExceptionHelper.getInvalidFieldException(Role.class, "authorities");
        }

    }

    private boolean isRoleWithNameAlreadyExists(UUID id, String name) {
        Role role = roleRepository.findOneByName(name).orElse(null);
        if (Objects.isNull(role)) {
            return false;
        }
        return !role.getId().equals(id);
    }
}
