package com.emetaplus.workplace.userworkplace.controller;

import com.emetaplus.workplace.user.service.UserService;
import com.emetaplus.workplace.userworkplace.dto.WorkplaceAdminDataDTO;
import com.emetaplus.workplace.userworkplace.service.UserWorkplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/workplace")
@RequiredArgsConstructor
public class AdminWorkplaceController {

    private static final String CREATE_WORKPLACE_FROM_ADMIN_PATH = "create";

    private final UserWorkplaceService userWorkplaceService;

    @PostMapping(CREATE_WORKPLACE_FROM_ADMIN_PATH)
    public void createUserWorkplaceData(@RequestBody WorkplaceAdminDataDTO dto) {
        userWorkplaceService.createWorkplaceFromAdmin(dto);
    }
}
