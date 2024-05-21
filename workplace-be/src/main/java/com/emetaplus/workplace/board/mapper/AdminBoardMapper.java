package com.emetaplus.workplace.board.mapper;

import com.emetaplus.workplace.board.dto.AdminBoardDTO;
import com.emetaplus.workplace.board.dto.SessionBoardDTO;
import com.emetaplus.workplace.board.model.AdminBoard;
import com.emetaplus.workplace.board.model.SessionBoard;
import com.emetaplus.workplace.board.service.AdminBoardService;
import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.userdeck.dto.AdminDeckDTO;
import com.emetaplus.workplace.userdeck.model.AdminDeck;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminBoardMapper extends AbstractMapperFactory {

    private final MinioService minioService;
    private final AdminBoardService adminBoardService;

    protected AdminBoardMapper(ModelMapper mapper, MinioService minioService, AdminBoardService adminBoardService) {
        super(mapper);
        this.minioService = minioService;
        this.adminBoardService = adminBoardService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<AdminBoard, AdminBoardDTO> adminBoardInstance = new MappingInstance<>(AdminBoard.class, AdminBoardDTO.class);
        adminBoardInstance.setPostMappingToDto((board, boardDTO) -> {
            boardDTO.setImageUrl(minioService.getBoardImageFromAdmin(board.getImage()));
            boardDTO.setBoardInCollection(adminBoardService.isBoardInCollection(board.getAdminId()));
        });
        builder
                .add(adminBoardInstance);
    }

    public AdminBoard toEntity(AdminBoardDTO dto) {
        return getMapper(AdminBoard.class, AdminBoardDTO.class).toEntity(dto);
    }

    public AdminBoardDTO toDTO(AdminBoard card) {
        return getMapper(AdminBoard.class, AdminBoardDTO.class).toDTO(card);
    }

    public List<AdminBoardDTO> toAdminBoardsDTOList(List<AdminBoard> boards) {
        return getMapper(AdminBoard.class, AdminBoardDTO.class).toDTO(boards);
    }
}
