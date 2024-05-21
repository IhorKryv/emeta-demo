package com.emetaplus.admin.role.service;

import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.core.query.PageableRequestQuery;
import com.emetaplus.admin.core.utils.SpecificationUtils;
import com.emetaplus.admin.role.model.Role;
import com.emetaplus.admin.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleValidator roleValidator;

    public Role createRole(Role body) {
        roleValidator.validateRoleForCreate(body);

        Role role = new Role();
        role.setName(body.getName());
        role.setDescription(body.getDescription());
        role.setAuthorities(body.getAuthorities());
        return roleRepository.save(role);
    }

    public Role updateRole(UUID id, Role body) {
        roleValidator.validateRoleForUpdate(id, body);

        Role existingRole = getById(id);
        existingRole.setName(body.getName());
        existingRole.setDescription(body.getDescription());
        existingRole.setAuthorities(body.getAuthorities());
        return roleRepository.save(existingRole);
    }

    public void deleteRole(UUID id) {
        Role role = getById(id);
        roleValidator.validateRoleForDelete(id);
        roleRepository.delete(role);
    }

    public Page<Role> getAllRoles(PageableRequestQuery pageableRequestQuery) {
        Specification<Role> specification = getSpecification(pageableRequestQuery.getSearchText());
        return roleRepository.findAll(specification, pageableRequestQuery.getPageable());
    }

    public Role getRoleById(UUID id) {
        return getById(id);
    }

    public Set<Role> getRolesByNames(List<String> names) {
        return new HashSet<>(roleRepository.findAllByNameIn(names));
    }

    private Role getById(UUID id) {
        return roleRepository
                .findById(id)
                .orElseGet(() -> {
                    log.error("[Role Exception]: Role with id={} not found", id);
                    throw ExceptionHelper.getNotFoundException(Role.class);
                });
    }

    private Specification<Role> getSpecification(String searchText) {
        return SpecificationUtils.getSearchSpecification(searchText, root ->
                Arrays.asList(root.get(Role.Fields.name), root.get(Role.Fields.description))
        );
    }

}
