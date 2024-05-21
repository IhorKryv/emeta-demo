package com.emetaplus.admin.role.controller;

import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.query.PageableRequestQuery;
import com.emetaplus.admin.role.dto.RoleDTO;
import com.emetaplus.admin.role.mapper.RoleMapper;
import com.emetaplus.admin.role.model.Role;
import com.emetaplus.admin.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@CrossOrigin
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper mapper;

    private static final String ROLE_CREATE_PATH = "create";
    private static final String ROLE_UPDATE_PATH = "update/{id}";
    private static final String ROLE_GET_SINGLE_PATH = "get/{id}";
    private static final String ROLE_GET_ALL_PATH = "get/all";
    private static final String ROLE_DELETE_PATH = "delete/{id}";


    @PostMapping(ROLE_CREATE_PATH)
    @PreAuthorize("hasAuthority('ROLE_MANAGER_AUTHORITY')")
    public RoleDTO createRole(@RequestBody RoleDTO body) {
        return mapper.toDTO(roleService.createRole(mapper.toEntity(body)));
    }

    @PutMapping(ROLE_UPDATE_PATH)
    @PreAuthorize("hasAuthority('ROLE_MANAGER_AUTHORITY')")
    public RoleDTO updateRole(@PathVariable UUID id, @RequestBody RoleDTO body) {
        return mapper.toDTO(roleService.updateRole(id, mapper.toEntity(body)));
    }

    @GetMapping(ROLE_GET_SINGLE_PATH)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER_AUTHORITY', 'USERS_MANAGER_AUTHORITY')")
    public Role getRoleById(@PathVariable UUID id) {
        return roleService.getRoleById(id);
    }

    @GetMapping(ROLE_GET_ALL_PATH)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER_AUTHORITY', 'USERS_MANAGER_AUTHORITY')")
    public PageDTO<RoleDTO> getAllRoles(PageableRequestQuery pageableRequestQuery) {
        return mapper.toRolePageDTO(roleService.getAllRoles(pageableRequestQuery));
    }

    @DeleteMapping(ROLE_DELETE_PATH)
    @PreAuthorize("hasAuthority('ROLE_MANAGER_AUTHORITY')")
    public void deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
    }
}
