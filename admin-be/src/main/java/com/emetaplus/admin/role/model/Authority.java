package com.emetaplus.admin.role.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {
    SUPER_USER_AUTHORITY("User with this authority has full access to the application"),
    USERS_MANAGER_AUTHORITY("User with this authority has access to manage users"),
    PLAN_MANAGER_AUTHORITY("User with this authority has access to manage plans"),
    ROLE_MANAGER_AUTHORITY("User with this authority has access to manage roles");


    String value;

    Authority(String authority) {
        this.value = authority;
    }

}
