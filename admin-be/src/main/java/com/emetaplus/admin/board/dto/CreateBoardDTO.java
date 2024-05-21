package com.emetaplus.admin.board.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateBoardDTO {
    private String name;
    private String description;
    private MultipartFile file;
}
