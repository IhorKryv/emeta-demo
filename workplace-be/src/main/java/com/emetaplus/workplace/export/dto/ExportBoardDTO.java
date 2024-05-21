package com.emetaplus.workplace.export.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ExportBoardDTO {
    private UUID id;
    private UUID workplaceId;
    private String name;
    private String description;
    private String imageURL;
    private boolean adminBoard;
}
