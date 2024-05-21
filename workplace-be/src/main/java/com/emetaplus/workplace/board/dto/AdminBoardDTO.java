package com.emetaplus.workplace.board.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AdminBoardDTO {
    private UUID id;
    private UUID adminId;
    private String name;
    private String customName;
    private String description;
    private String customDescription;
    private String image;
    private String imageUrl;
    private boolean boardInCollection;
    private UUID workplaceId;
}
