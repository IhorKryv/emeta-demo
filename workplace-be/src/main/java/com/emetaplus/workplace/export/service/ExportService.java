package com.emetaplus.workplace.export.service;

import com.emetaplus.workplace.board.model.AdminBoard;
import com.emetaplus.workplace.board.model.SessionBoard;
import com.emetaplus.workplace.board.service.AdminBoardService;
import com.emetaplus.workplace.board.service.SessionBoardService;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.export.dto.ExportBoardDTO;
import com.emetaplus.workplace.export.dto.ExportDeckDTO;
import com.emetaplus.workplace.userdeck.dto.CardDTO;
import com.emetaplus.workplace.userdeck.dto.CardWithSizeDTO;
import com.emetaplus.workplace.userdeck.mapper.CardMapper;
import com.emetaplus.workplace.userdeck.model.AdminDeck;
import com.emetaplus.workplace.userdeck.model.Card;
import com.emetaplus.workplace.userdeck.model.UserDeck;
import com.emetaplus.workplace.userdeck.service.AdminDeckService;
import com.emetaplus.workplace.userdeck.service.UserDeckService;
import freemarker.template.utility.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final UserDeckService userDeckService;
    private final AdminDeckService adminDeckService;

    private final SessionBoardService boardService;
    private final AdminBoardService adminBoardService;

    private final MinioService minioService;

    public List<ExportDeckDTO> getDecksForExport() {
        List<ExportDeckDTO> allDecksForExport = new ArrayList<>();
        List<AdminDeck> adminDecks = adminDeckService.getAllSelectedDecks();
        List<UserDeck> userDecks = userDeckService.getAllDecks();
        allDecksForExport.addAll(adminDecks.stream().map(adminDeck -> new ExportDeckDTO(
                adminDeck.getAdminId(),
                adminDeck.getWorkplaceId(),
                adminDeck.getName(),
                adminDeck.getDescription(),
                getAdminDeckCardBackUrl(adminDeck),
                adminDeck.getCardsCount(),
                true
        )).toList());
        allDecksForExport.addAll(userDecks.stream().map(userDeck -> new ExportDeckDTO(
                userDeck.getId(),
                userDeck.getWorkplaceId(),
                userDeck.getName(),
                userDeck.getDescription(),
                getUserDeckCardBackUrl(userDeck),
                userDeck.getCardsCount(),
                false
        )).toList());
        return allDecksForExport;
    }

    public List<ExportBoardDTO> getBoardsForExport() {
        List<ExportBoardDTO> allBoardsForExport = new ArrayList<>();
        List<AdminBoard> adminBoards = adminBoardService.getAllSelectedBoards();
        List<SessionBoard> sessionBoards = boardService.getAllSessionBoards();
        allBoardsForExport.addAll(adminBoards.stream().map(adminBoard -> new ExportBoardDTO(
                adminBoard.getAdminId(),
                adminBoard.getWorkplaceId(),
                adminBoard.getName(),
                adminBoard.getDescription(),
                getAdminBoardImage(adminBoard),
                true
        )).toList());
        allBoardsForExport.addAll(sessionBoards.stream().map(sessionBoard -> new ExportBoardDTO(
                sessionBoard.getId(),
                sessionBoard.getWorkplaceId(),
                sessionBoard.getName(),
                sessionBoard.getDescription(),
                getSessionBoardImage(sessionBoard),
                false
        )).toList());
        return allBoardsForExport;
    }

    public List<Card> getCardsForWorkplaceDeck(UUID deckId) {
        try {
            return userDeckService.getAllCards(deckId);
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }
    }

    public List<CardWithSizeDTO> getCardsForAdminDeck(UUID adminId) {
        try {
            return adminDeckService.getAllCardsWithSizeForDeck(adminId);
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }

    }

    private String getAdminDeckCardBackUrl(AdminDeck deck) {
        return StringUtils.isBlank(deck.getCardBack())
                ? ""
                : minioService.getCardImageFromAdmin(deck.getAdminId().toString(), deck.getCardBack(), deck.getCardBackExtension());
    }

    private String getUserDeckCardBackUrl(UserDeck deck) {
        return minioService.getCardImage(
                StringUtils.isBlank(deck.getCardBack())
                ? ""
                : deck.getWorkplaceId().toString(), deck.getId().toString(), deck.getCardBack(), deck.getCardBackExtension()
        );
    }

    private String getAdminBoardImage(AdminBoard board) {
        return minioService.getBoardImageFromAdmin(board.getImage());
    }

    private String getSessionBoardImage(SessionBoard board) {
        return minioService.getBoardImage(board.getWorkplaceId().toString(), board.getImage(), board.getImageExtension());
    }
}
