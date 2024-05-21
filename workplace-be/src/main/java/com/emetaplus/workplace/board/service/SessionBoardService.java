package com.emetaplus.workplace.board.service;

import com.emetaplus.workplace.board.model.SessionBoard;
import com.emetaplus.workplace.board.repository.SessionBoardRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.core.query.PageableRequestQuery;
import com.emetaplus.workplace.core.utils.SpecificationUtils;
import com.emetaplus.workplace.file.service.FileService;
import com.emetaplus.workplace.file.utils.FileUtils;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.user.model.User;
import com.emetaplus.workplace.userdeck.model.UserDeck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionBoardService {

    private final SessionBoardRepository sessionBoardRepository;
    private final SessionBoardValidator sessionBoardValidator;
    private final MinioService minioService;

    public SessionBoard createSessionBoard(SessionBoard sessionBoard) {
        sessionBoardValidator.validateSessionBoardForCreate(sessionBoard);
        User currentUser = UserUtils.getCurrentUser();
        sessionBoard.setWorkplaceId(currentUser.getWorkplaceId());
        return sessionBoardRepository.save(sessionBoard);
    }

    public SessionBoard setSessionBoardImage(UUID id, MultipartFile file) {
        SessionBoard sessionBoard = getById(id);
        UUID existingSessionBoardId = sessionBoard.getId();
        String filename = String.format("board_%s", existingSessionBoardId);
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        uploadBoardImage(sessionBoard, filename, extension, file);
        sessionBoard.setImage(filename);
        sessionBoard.setImageExtension(extension);
        return sessionBoardRepository.save(sessionBoard);
    }

    public SessionBoard getSingleSessionBoard(UUID id) {
        return getById(id);
    }

    public Page<SessionBoard> getAllSessionBoards(PageableRequestQuery pageableRequestQuery) {
        UUID workplaceId = UserUtils.getCurrentUser().getWorkplaceId();
        Specification<SessionBoard> specification = getSpecification(workplaceId, pageableRequestQuery.getSearchText());
        return sessionBoardRepository.findAll(specification, pageableRequestQuery.getPageable());
    }

    public List<SessionBoard> getAllSessionBoards() {
        UUID workplaceId = UserUtils.getCurrentUser().getWorkplaceId();
        return sessionBoardRepository.findAllByWorkplaceId(workplaceId);
    }

    public SessionBoard updateSessionBoard(UUID id, SessionBoard sessionBoard) {
        SessionBoard existingBoard = getById(id);
        sessionBoardValidator.validateSessionBoardForUpdate(id, sessionBoard);
        existingBoard.setName(sessionBoard.getName());
        existingBoard.setDescription(sessionBoard.getDescription());
        return sessionBoardRepository.save(existingBoard);
    }

    @Transactional
    public void deleteSessionBoard(UUID id) {
        SessionBoard existingBoard = getById(id);
        sessionBoardRepository.delete(existingBoard);
        removeOldBoardImageIfExists(existingBoard);

    }

    private SessionBoard getById(UUID id) {
        SessionBoard sessionBoard = sessionBoardRepository.findById(id).orElseGet(() -> {
            log.error("[Session Board Exception]: Session board with id={} not found", id);
            throw ExceptionHelper.getNotFoundException(SessionBoard.class);
        });
        UserUtils.isUserHasAccess(sessionBoard.getWorkplaceId());
        return sessionBoard;

    }

    private Specification<SessionBoard> getSpecification(UUID workplaceId, String searchText) {
        Specification<SessionBoard> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserDeck.Fields.workplaceId), workplaceId);
        specification.and(
                SpecificationUtils.getSearchSpecification(searchText, root ->
                        Arrays.asList(root.get(UserDeck.Fields.name), root.get(UserDeck.Fields.description))
                )
        );
        return specification;
    }

    private void uploadBoardImage(SessionBoard sessionBoard, String filename, String extension, MultipartFile file) {
        removeOldBoardImageIfExists(sessionBoard);
        try {
            minioService.addBoardImage(
                    sessionBoard.getWorkplaceId().toString(),
                    filename,
                    extension,
                    new ByteArrayInputStream(file.getBytes()),
                    file.getSize());
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }
    }

    private void removeOldBoardImageIfExists(SessionBoard sessionBoard) {
        if (StringUtils.isNotBlank(sessionBoard.getImage())) {
            minioService.removeBoardImage(
                    sessionBoard.getWorkplaceId().toString(),
                    sessionBoard.getImage(),
                    sessionBoard.getImageExtension()
            );
        }
    }


}
