package com.emetaplus.workplace.board.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionBoardDTO {
    private UUID id;
    private String name;
    private String description;
    private String image;
    private String imageUrl;
}
