package com.emetaplus.workplace.client.controller;

import com.emetaplus.workplace.client.dto.ClientRecordDTO;
import com.emetaplus.workplace.client.dto.ClientRecordUpdateDTO;
import com.emetaplus.workplace.client.mapper.ClientRecordMapper;
import com.emetaplus.workplace.client.service.ClientRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/client-record")
@RequiredArgsConstructor
public class ClientRecordController {

    private final ClientRecordService clientRecordService;
    private final ClientRecordMapper clientRecordMapper;

    @PostMapping("/{clientId}/create")
    public ClientRecordDTO createClientRecord(@PathVariable UUID clientId, @RequestBody ClientRecordDTO dto) {
        return clientRecordMapper.toDTO(clientRecordService.createRecord(clientId, clientRecordMapper.toEntity(dto)));
    }

    @PostMapping("/{clientRecordId}/files/add")
    public void addFilesToClientRecord(@PathVariable UUID clientRecordId, @RequestParam(name = "files") List<MultipartFile> files) {
        clientRecordService.addFilesToClient(files, clientRecordId);
    }

    @PutMapping("/{clientRecordId}/update")
    public ClientRecordDTO updateRecord(@PathVariable UUID clientRecordId, @RequestBody ClientRecordUpdateDTO dto) {
        return clientRecordMapper.toDTO(clientRecordService.updateClientRecord(clientRecordId, clientRecordMapper.toUpdateEntity(dto)));
    }

    @GetMapping("/{clientId}/all")
    public List<ClientRecordDTO> getAllClientRecords(@PathVariable UUID clientId) {
        return clientRecordMapper.toClientRecordDTOList(clientRecordService.getAllClientRecords(clientId));
    }

    @DeleteMapping("/{clientId}/remove/{clientRecordId}")
    public void removeRecord(@PathVariable UUID clientId, @PathVariable UUID clientRecordId) {
        clientRecordService.removeClientRecord(clientId, clientRecordId);
    }

    @DeleteMapping("/{clientId}/remove/file/{fileId}")
    public void removeFileFromRecord(@PathVariable UUID clientId, @PathVariable UUID fileId) {
        clientRecordService.removeFileFromClient(clientId, fileId);
    }
}
