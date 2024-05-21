package com.emetaplus.workplace.client.mapper;

import com.emetaplus.workplace.client.dto.ClientFileDTO;
import com.emetaplus.workplace.client.dto.ClientRecordDTO;
import com.emetaplus.workplace.client.dto.ClientRecordUpdateDTO;
import com.emetaplus.workplace.client.model.ClientFile;
import com.emetaplus.workplace.client.model.ClientRecord;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.user.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ClientRecordMapper extends AbstractMapperFactory {

    private final MinioService minioService;

    protected ClientRecordMapper(ModelMapper mapper, MinioService minioService) {
        super(mapper);
        this.minioService = minioService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<ClientRecord, ClientRecordDTO> clientRecordInstance = new MappingInstance<>(ClientRecord.class, ClientRecordDTO.class);
        clientRecordInstance.setPostMappingToDto((clientRecord, clientRecordDTO) -> {
            User user = UserUtils.getCurrentUser();
            List<ClientFileDTO> dtoFiles = new ArrayList<>();
            clientRecord.getFiles().forEach(file -> {
                String url = minioService.getClientFile(
                        user.getWorkplaceId().toString(),
                        clientRecord.getClientId().toString(),
                        clientRecord.getId().toString(),
                        file.getName(),
                        file.getExtension()
                );
                ClientFileDTO fileDTO = new ClientFileDTO();
                fileDTO.setId(file.getId());
                fileDTO.setClientRecordId(file.getClientRecordId());
                fileDTO.setName(file.getName());
                fileDTO.setUrl(url);
                dtoFiles.add(fileDTO);
            });
           clientRecordDTO.setFiles(dtoFiles);
        });
        MappingInstance<ClientRecord, ClientRecordUpdateDTO> clientRecordUpdateInstance = new MappingInstance<>(ClientRecord.class, ClientRecordUpdateDTO.class);

        builder
                .add(clientRecordInstance)
                .add(clientRecordUpdateInstance);
    }

    public ClientRecord toEntity(ClientRecordDTO dto) {
        return getMapper(ClientRecord.class, ClientRecordDTO.class).toEntity(dto);
    }

    public ClientRecord toUpdateEntity(ClientRecordUpdateDTO dto) {
        return getMapper(ClientRecord.class, ClientRecordUpdateDTO.class).toEntity(dto);
    }

    public ClientRecordDTO toDTO(ClientRecord record) {
        return getMapper(ClientRecord.class, ClientRecordDTO.class).toDTO(record);
    }

    public List<ClientRecord> toClientRecordList(List<ClientRecordDTO> clientRecordDTOs) {
        return getMapper(ClientRecord.class, ClientRecordDTO.class).toEntityList(clientRecordDTOs);
    }

    public List<ClientRecordDTO> toClientRecordDTOList(List<ClientRecord> clientRecords) {
        return getMapper(ClientRecord.class, ClientRecordDTO.class).toDtoList(clientRecords);
    }
}
