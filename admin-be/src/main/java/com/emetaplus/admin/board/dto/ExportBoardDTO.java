package com.emetaplus.admin.board.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ExportBoardDTO {
    private UUID adminId;
    private String name;
    private String description;
    private String image;
    private String imageUrl;
}
