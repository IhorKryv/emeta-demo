package com.emetaplus.workplace.client.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientRecordUpdateDTO {
    private String title;
    private String content;

    private LocalDateTime sessionDate;
}
