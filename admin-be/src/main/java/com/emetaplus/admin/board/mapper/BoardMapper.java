package com.emetaplus.admin.board.mapper;

import com.emetaplus.admin.board.dto.BoardDTO;
import com.emetaplus.admin.board.model.Board;
import com.emetaplus.admin.core.mapper.AbstractMapperFactory;
import com.emetaplus.admin.core.mapper.MappingInstance;
import com.emetaplus.admin.core.mapper.MappingInstancesBuilder;
import com.emetaplus.admin.core.minio.MinioService;
import com.emetaplus.admin.deck.dto.DeckDTO;
import com.emetaplus.admin.deck.model.Deck;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class BoardMapper extends AbstractMapperFactory {

    private final MinioService minioService;

    protected BoardMapper(ModelMapper mapper, MinioService minioService) {
        super(mapper);
        this.minioService = minioService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Board, BoardDTO> boardInstance = new MappingInstance<>(Board.class, BoardDTO.class);
        boardInstance.setPostMappingToDto((board, boardDTO) -> {
            String boardImage = Objects.nonNull(board.getImage())
                    ? minioService.getBoardImage(board.getImage())
                    : null;
            boardDTO.setImageUrl(boardImage);
        });
        builder.add(
                boardInstance
        );
    }

    public Board toEntity(BoardDTO dto) {
        return getMapper(Board.class, BoardDTO.class).toEntity(dto);
    }

    public BoardDTO toDTO(Board board) {
        return getMapper(Board.class, BoardDTO.class).toDTO(board);
    }

    public List<BoardDTO> toDTOList(List<Board> boards) {
        return getMapper(Board.class, BoardDTO.class).toDTO(boards);
    }
}
