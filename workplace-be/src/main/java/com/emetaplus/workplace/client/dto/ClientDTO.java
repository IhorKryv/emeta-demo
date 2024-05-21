package com.emetaplus.workplace.client.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientDTO {

    private UUID id;
    private String firstName;
    private String lastName = "";
    private String email = "";
    private String phone = "";
    private boolean onSession = false;

}
