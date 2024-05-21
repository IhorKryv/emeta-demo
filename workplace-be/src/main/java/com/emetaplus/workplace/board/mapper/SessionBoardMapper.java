package com.emetaplus.workplace.board.mapper;

import com.emetaplus.workplace.board.dto.SessionBoardDTO;
import com.emetaplus.workplace.board.model.SessionBoard;
import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.core.minio.MinioService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SessionBoardMapper extends AbstractMapperFactory {

    private final MinioService minioService;

    protected SessionBoardMapper(ModelMapper mapper, MinioService minioService) {
        super(mapper);
        this.minioService = minioService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<SessionBoard, SessionBoardDTO> boardInstance = new MappingInstance<>(SessionBoard.class, SessionBoardDTO.class);
        boardInstance.setPostMappingToDto((sessionBoard, dto) -> dto.setImageUrl(
                Objects.isNull(sessionBoard.getImage())
                        ? null
                        : minioService.getBoardImage(
                                sessionBoard.getWorkplaceId().toString(),
                        sessionBoard.getImage(),
                        sessionBoard.getImageExtension()
                )
        ));
        builder
                .add(boardInstance);
    }

    public SessionBoard toEntity(SessionBoardDTO dto) {
        return getMapper(SessionBoard.class, SessionBoardDTO.class).toEntity(dto);
    }

    public SessionBoardDTO toDTO(SessionBoard card) {
        return getMapper(SessionBoard.class, SessionBoardDTO.class).toDTO(card);
    }

    public PageDTO<SessionBoardDTO> toBoardsPageDTO(Page<SessionBoard> boards) {
        return getMapper(SessionBoard.class, SessionBoardDTO.class).toDTO(boards);
    }
}
