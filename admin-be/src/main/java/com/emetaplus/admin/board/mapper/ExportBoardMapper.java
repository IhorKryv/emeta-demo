package com.emetaplus.admin.board.mapper;

import com.emetaplus.admin.board.dto.BoardDTO;
import com.emetaplus.admin.board.dto.ExportBoardDTO;
import com.emetaplus.admin.board.model.Board;
import com.emetaplus.admin.core.mapper.AbstractMapperFactory;
import com.emetaplus.admin.core.mapper.MappingInstance;
import com.emetaplus.admin.core.mapper.MappingInstancesBuilder;
import com.emetaplus.admin.core.minio.MinioService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ExportBoardMapper extends AbstractMapperFactory {

    private final MinioService minioService;


    protected ExportBoardMapper(ModelMapper mapper, MinioService minioService) {
        super(mapper);
        this.minioService = minioService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Board, ExportBoardDTO> boardInstance = new MappingInstance<>(Board.class, ExportBoardDTO.class);
        boardInstance.setPostMappingToDto((board, boardDTO) -> {
            boardDTO.setAdminId(board.getId());
            String boardImage = Objects.nonNull(board.getImage())
                    ? minioService.getBoardImage(board.getImage())
                    : null;
            boardDTO.setImageUrl(boardImage);
        });
        builder.add(
                boardInstance
        );
    }

    public Board toEntity(ExportBoardDTO dto) {
        return getMapper(Board.class, ExportBoardDTO.class).toEntity(dto);
    }

    public ExportBoardDTO toDTO(Board board) {
        return getMapper(Board.class, ExportBoardDTO.class).toDTO(board);
    }

    public List<ExportBoardDTO> toDTOList(List<Board> boards) {
        return getMapper(Board.class, ExportBoardDTO.class).toDTO(boards);
    }
}
