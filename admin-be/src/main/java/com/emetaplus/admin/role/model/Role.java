package com.emetaplus.admin.role.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "roles")
@FieldNameConstants
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private UUID id;
    private String name;
    private String description;

    private String authorities;

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public List<Authority> getAuthorities() {
        return Arrays.stream(this.authorities.split(",")).map(Authority::valueOf).toList();
    }
}
