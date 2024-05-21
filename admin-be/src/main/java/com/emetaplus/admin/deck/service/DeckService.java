package com.emetaplus.admin.deck.service;

import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.core.minio.MinioClientBuilder;
import com.emetaplus.admin.core.minio.MinioService;
import com.emetaplus.admin.core.minio.MinioStorageUtils;
import com.emetaplus.admin.core.query.PageableRequestQuery;
import com.emetaplus.admin.core.utils.SpecificationUtils;
import com.emetaplus.admin.deck.model.Card;
import com.emetaplus.admin.deck.model.Deck;
import com.emetaplus.admin.deck.repository.CardRepository;
import com.emetaplus.admin.deck.repository.DeckRepository;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeckService {

    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final DeckValidator deckValidator;
    private final MinioService minioService;
    private final MinioClientBuilder minioClientBuilder;

    public Deck createDeck(Deck deck) {
        deckValidator.validateDeckForCreate(deck);
        deck.setCardList(new ArrayList<>());
        deck = deckRepository.save(deck);
        try {
            minioClientBuilder.getMinioClient().putObject(
                    PutObjectArgs.builder()
                            .bucket(MinioStorageUtils.getEMetaAdminBucket())
                            .object(MinioStorageUtils.getEMetaAdminDeckPath(deck.getId().toString() + "/"))
                            .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build()
            );
        } catch (Exception e) {
            throw ExceptionHelper.getMinioException(e.getMessage());
        }
        return deck;
    }

    @Transactional
    public void addCardsToDeck(List<MultipartFile> cards, UUID deckId) {
        Deck deck = getDeckById(deckId);
        cards.forEach(card -> {
            try {
                String cardExtension = FilenameUtils.getExtension(card.getOriginalFilename());
                Card cardToSave = new Card(deckId);
                cardToSave.setExtension(cardExtension);
                Card savedCard = cardRepository.save(cardToSave);
                minioService.addCardImage(
                        deckId.toString(),
                        savedCard.getId().toString(),
                        savedCard.getExtension(),
                        new ByteArrayInputStream(card.getBytes()),
                        card.getSize()
                );
                deck.getCardList().add(savedCard);
                deck.setCardsCount(deck.getCardList().size());
            } catch (Exception e) {
                throw ExceptionHelper.getInternalServerError();
            }
        });
        deckRepository.save(deck);
    }

    @Transactional
    public void updateCardback(UUID deckId, MultipartFile file) {
        Deck deck = getDeckById(deckId);
        String filename = String.format("cardback_%s", deck.getId());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        uploadCardBack(deck, filename, extension, file);
        deck.setCardBack(filename);
        deck.setCardBackExtension(extension);
        deckRepository.save(deck);
    }

    public Page<Card> getAllCardsInDeck(UUID deckId, PageableRequestQuery pageableRequestQuery) {
        List<Card> cards = getDeckById(deckId).getCardList();
        Pageable pageable = pageableRequestQuery.getPageable();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), cards.size());
        return new PageImpl<>(cards.subList(start, end), pageableRequestQuery.getPageable(), cards.size());
    }

    public List<Card> getAllCardsInDeckForWorkplace(UUID deckId) {
        return getDeckById(deckId).getCardList();
    }

    public void removeCardFromDeck(UUID deckId, UUID cardId, String extension) {
        cardRepository.delete(getCardById(cardId));
        Deck deck = getDeckById(deckId);
        minioService.removeCardImage(deckId.toString(), cardId.toString(), extension);
        deck.setCardsCount(deck.getCardsCount() - 1);
        deckRepository.save(deck);
    }

    @Transactional
    public void removeCardbackFromDeck(UUID id) {
        Deck deck = getDeckById(id);
        minioService.removeCardImage(deck.getId().toString(), deck.getCardBack(), deck.getCardBackExtension());
        deck.setCardBack(null);
        deck.setCardBackExtension(null);
        deckRepository.save(deck);
    }

    public Deck updateDeck(UUID id, Deck deck) {
        deckValidator.validateDeckForUpdate(id, deck);
        Deck existingDeck = getDeckById(id);
        existingDeck.setName(deck.getName());
        existingDeck.setDescription(deck.getDescription());
        existingDeck.setPremium(deck.isPremium());
        existingDeck.setAvailable(deck.isAvailable());
        existingDeck.setCategories(deck.getCategories());
        return deckRepository.save(existingDeck);
    }

    public void makeCardAsCardback(UUID deckId, UUID card, String extension) {
        if (!isCardExistsInDeck(deckId, card)) {
            //TODO: Add exception
            throw ExceptionHelper.getInternalServerError();
        }
        Deck deck = getDeckById(deckId);
        String cardbackName = "/cardback_" + card;
        if (StringUtils.isNotBlank(deck.getCardBack())) {
            minioService.removeCardImage(deckId.toString(), deck.getCardBack(), deck.getCardBackExtension());
        }
        try {
            minioClientBuilder.getMinioClient().copyObject(CopyObjectArgs.builder()
                    .bucket(MinioStorageUtils.getEMetaAdminBucket())
                    .object(MinioStorageUtils.getEMetaAdminDeckPath(deckId.toString()) + cardbackName + "." + extension)
                    .source(CopySource.builder()
                            .bucket(MinioStorageUtils.getEMetaAdminBucket())
                            .object(MinioStorageUtils.getEMetaAdminCardPath(deckId.toString(), card.toString() + "." + extension))
                            .build())
                    .build());
        } catch (Exception e) {
            throw ExceptionHelper.getMinioImageException("file");
        }
        removeCardFromDeck(deckId, card, extension);
        deck = getDeckById(deckId);
        deck.setCardBack(cardbackName);
        deck.setCardBackExtension(extension);
        deckRepository.save(deck);

    }

    public Deck getSingleDeck(UUID id) {
        return getDeckById(id);
    }

    public Page<Deck> getAllDecks(PageableRequestQuery pageableRequestQuery) {
        Specification<Deck> specification = getSpecification(pageableRequestQuery.getSearchText());
        return deckRepository.findAll(specification, pageableRequestQuery.getPageable());
    }

    public List<Deck> getAllDecksForWorkplace() {
        return deckRepository.findAllByAvailableTrue();
    }

    public List<Deck> getSelectedDecksForWorkplace(List<UUID> selectedDeckIds) {
        return deckRepository.findAllByIdIn(selectedDeckIds);
    }

    public void deleteDeckAndAllCardsInside(UUID id) {
        Deck deck = getDeckById(id);
        minioService.removeDeckWithContent(deck.getId().toString());
        deckRepository.delete(deck);
    }

    private void uploadCardBack(Deck deck, String filename, String extension, MultipartFile file) {
        if (StringUtils.isNotBlank(deck.getCardBack())) {
            minioService.removeCardImage(deck.getId().toString(), deck.getCardBack(), deck.getCardBackExtension());
        }
        try {
            minioService.addCardImage(deck.getId().toString(), filename, extension, new ByteArrayInputStream(file.getBytes()), file.getSize());
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }
    }

    private Deck getDeckById(UUID id) {
        return deckRepository.findById(id).orElseGet(() -> {
            log.error("[Deck Exception]: Deck with id={} not found", id);
            throw ExceptionHelper.getNotFoundException(Deck.class);
        });
    }

    private Card getCardById(UUID id) {
        return cardRepository.findById(id).orElseGet(() -> {
            log.error("[Card Exception]: Card with id={} not found", id);
            throw ExceptionHelper.getNotFoundException(Card.class);
        });
    }

    private boolean isCardExistsInDeck(UUID deckId, UUID cardId) {
        return getCardById(cardId).getDeckId().equals(deckId);
    }

    private Specification<Deck> getSpecification(String searchText) {
        return SpecificationUtils.getSearchSpecification(searchText, root ->
                Arrays.asList(root.get(Deck.Fields.name), root.get(Deck.Fields.description))
        );
    }
}
