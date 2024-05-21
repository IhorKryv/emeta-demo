package com.emetaplus.workplace.client.service;

import com.emetaplus.workplace.client.dto.ClientRecordDTO;
import com.emetaplus.workplace.client.model.Client;
import com.emetaplus.workplace.client.model.ClientFile;
import com.emetaplus.workplace.client.model.ClientRecord;
import com.emetaplus.workplace.client.repository.ClientFileRepository;
import com.emetaplus.workplace.client.repository.ClientRecordRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.user.model.User;
import com.emetaplus.workplace.userdeck.model.Card;
import com.emetaplus.workplace.userdeck.model.UserDeck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientRecordService {

    private final ClientService clientService;
    private final ClientRecordRepository clientRecordRepository;
    private final ClientFileRepository clientFileRepository;
    private final MinioService minioService;

    @Transactional
    public ClientRecord createRecord(UUID clientId, ClientRecord clientRecord) {
        Client client = clientService.getClient(clientId);
        clientRecord.setCreatedDate(LocalDateTime.now());
        clientRecord = clientRecordRepository.save(clientRecord);
        client.getRecords().add(clientRecord);
        clientService.updateClient(clientId, client);
        return clientRecord;
    }

    public void addFilesToClient(List<MultipartFile> files, UUID clientRecordId) {
        User currentUser = UserUtils.getCurrentUser();
        ClientRecord clientRecord = getClientRecordById(clientRecordId);
        files.forEach(file -> {
            try {
                ClientFile clientFile = new ClientFile();
                clientFile.setClientRecordId(clientRecord.getId());
                clientFile.setName(new Timestamp(new Date().getTime()) + "-" + FilenameUtils.getBaseName(file.getOriginalFilename()));
                clientFile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
                clientFile = clientFileRepository.save(clientFile);
                minioService.addFileToClientRecord(
                        currentUser.getWorkplaceId().toString(),
                        clientRecord.getClientId().toString(),
                        clientRecord.getId().toString(),
                        clientFile.getName(),
                        clientFile.getExtension(),
                        new ByteArrayInputStream(file.getBytes()),
                        file.getSize()
                );
                clientRecord.getFiles().add(clientFile);
            } catch (Exception e) {
                throw ExceptionHelper.getInternalServerError();
            }
        });
    }

    @Transactional
    public void removeClientRecord(UUID clientId, UUID clientRecordId) {
        ClientRecord clientRecord = getClientRecordById(clientRecordId);
        if (!CollectionUtils.isEmpty(clientRecord.getFiles())) {
            clientRecord.getFiles().forEach(file -> removeFileFromClient(clientId, file.getId()));
        }
        clientRecordRepository.delete(clientRecord);
    }

    @Transactional
    public void removeFileFromClient(UUID clientId, UUID fileId) {
        ClientFile clientFile = clientFileRepository.findById(fileId)
                .orElseThrow(() -> ExceptionHelper.getNotFoundException(ClientFile.class));
        final var workplaceId = UserUtils.getCurrentUser().getWorkplaceId();
        final var filename = clientFile.getName() + "." + clientFile.getExtension();
        try {
            minioService.removeRecordFile(workplaceId.toString(), clientId.toString(), clientFile.getClientRecordId().toString(), filename);
        } catch (Exception e) {
            log.error("File with current name do not exists current record folder on MINIO. Skipping...");
        }
        clientFileRepository.delete(clientFile);
    }

    public ClientRecord updateClientRecord(UUID clientRecordId, ClientRecord clientRecord) {
        ClientRecord existingRecord = getClientRecordById(clientRecordId);
        existingRecord.setTitle(clientRecord.getTitle());
        existingRecord.setContent(clientRecord.getContent());
        existingRecord.setSessionDate(clientRecord.getSessionDate());
        return clientRecordRepository.save(existingRecord);

    }

    public List<ClientRecord> getAllClientRecords(UUID clientId) {
        return clientRecordRepository.findAllByClientId(clientId)
                .stream()
                .sorted(Comparator.comparing(ClientRecord::getCreatedDate).reversed())
                .collect(Collectors.toList());
    }

    private ClientRecord getClientRecordById(UUID id) {
        return clientRecordRepository.findById(id).orElseThrow(() -> ExceptionHelper.getNotFoundException(ClientRecord.class));
    }
}
