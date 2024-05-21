package com.emetaplus.workplace.userdeck.service;

import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioClientBuilder;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.core.minio.MinioStorageUtils;
import com.emetaplus.workplace.core.query.PageableRequestQuery;
import com.emetaplus.workplace.core.utils.SpecificationUtils;
import com.emetaplus.workplace.file.service.FileService;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.user.model.User;
import com.emetaplus.workplace.userdeck.model.Card;
import com.emetaplus.workplace.userdeck.model.UserDeck;
import com.emetaplus.workplace.userdeck.repository.UserCardRepository;
import com.emetaplus.workplace.userdeck.repository.UserDeckRepository;
import com.emetaplus.workplace.userworkplace.model.UserWorkplace;
import com.emetaplus.workplace.userworkplace.model.UserWorkplaceSettings;
import com.emetaplus.workplace.userworkplace.repository.UserWorkplaceRepository;
import com.emetaplus.workplace.userworkplace.service.AdminWorkplaceService;
import com.emetaplus.workplace.userworkplace.service.UserWorkplaceService;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
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
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeckService {
    private final UserDeckRepository userDeckRepository;
    private final UserCardRepository userCardRepository;
    private final UserDeckValidator userDeckValidator;
    private final MinioService minioService;
    private final MinioClientBuilder minioClientBuilder;
    private final AdminWorkplaceService adminWorkplaceService;
    private final UserWorkplaceRepository userWorkplaceRepository;


    public UserDeck createUserDeck(UserDeck userDeck) {
        User currentUser = UserUtils.getCurrentUser();
        userDeckValidator.validateUserDeckForCreate(currentUser.getWorkplaceId(), userDeck);
        UserWorkplace workplace = userWorkplaceRepository.findById(currentUser.getWorkplaceId()).orElseThrow(() -> ExceptionHelper.getNotFoundException(UserWorkplace.class));
        UserWorkplaceSettings settings = adminWorkplaceService.getSettings(workplace.getWorkplaceId());
        if (!settings.isAllowOwnDecks()) {
            throw ExceptionHelper.getUserHasNoAccessException(UserDeck.class);
        }
        userDeck.setCardList(new ArrayList<>());
        userDeck.setWorkplaceId(currentUser.getWorkplaceId());
        userDeck.setCardBack(null);
        userDeck = userDeckRepository.save(userDeck);

        minioService.createDeckFolder(currentUser.getWorkplaceId().toString(), userDeck.getId().toString());
        return userDeck;
    }

    @Transactional
    public void addCardsToUserDeck(List<MultipartFile> cards, UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        UserDeck existingDeck = getUserDeckById(currentUser, id);
        cards.forEach(card -> {
            try {
                Card cardToCreate = new Card(existingDeck.getId());
                String cardExtension = FilenameUtils.getExtension(card.getOriginalFilename());
                cardToCreate.setExtension(cardExtension);
                Card savedCard = userCardRepository.save(cardToCreate);
                minioService.addCardImage(
                        currentUser.getWorkplaceId().toString(),
                        existingDeck.getId().toString(),
                        savedCard.getId().toString(),
                        savedCard.getExtension(),
                        new ByteArrayInputStream(card.getBytes()),
                        card.getSize()
                        );
                existingDeck.getCardList().add(savedCard);
                existingDeck.setCardsCount(existingDeck.getCardsCount() + 1);
            } catch (Exception e) {
                throw ExceptionHelper.getInternalServerError();
            }
        });
    }

    @Transactional
    public void updateCardback(UUID deckId, MultipartFile file) {
        User currentUser = UserUtils.getCurrentUser();
        UserDeck existingDeck = getUserDeckById(currentUser, deckId);
        String filename = String.format("cardback_%s", existingDeck.getId());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        uploadCardBack(existingDeck, filename, extension, file);
        existingDeck.setCardBack(filename);
        existingDeck.setCardBackExtension(extension);
        userDeckRepository.save(existingDeck);
    }

    public void makeCardAsCardback(UUID deckId, UUID card, String extension) {
        if (!isCardExistsInDeck(deckId, card)) {
            //TODO: Add exception
            throw ExceptionHelper.getInternalServerError();
        }
        User currentUser = UserUtils.getCurrentUser();
        UserDeck deck = getUserDeckById(currentUser, deckId);
        String cardbackName = "/cardback_" + card;
        if (StringUtils.isNotBlank(deck.getCardBack())) {
            minioService.removeCardImage(currentUser.getWorkplaceId().toString(), deckId.toString(), deck.getCardBack(), deck.getCardBackExtension());
        }
        try {
            minioClientBuilder.getMinioClient().copyObject(CopyObjectArgs.builder()
                    .bucket(MinioStorageUtils.getEMetaWorkplaceBucket())
                    .object(MinioStorageUtils.getEMetaWorkplaceDeckPath(currentUser.getWorkplaceId().toString(), deckId.toString()) + cardbackName + "." + extension)
                    .source(CopySource.builder()
                            .bucket(MinioStorageUtils.getEMetaWorkplaceBucket())
                            .object(MinioStorageUtils.getEMetaWorkplaceCardPath(currentUser.getWorkplaceId().toString(), deckId.toString(), card.toString() + "." + extension))
                            .build())
                    .build());
        } catch (Exception e) {
            throw ExceptionHelper.getMinioImageException("file");
        }
        removeCardFromDeck(deckId, card);
        deck = getUserDeckById(currentUser, deckId);
        deck.setCardBack(cardbackName);
        deck.setCardBackExtension(extension);
        userDeckRepository.save(deck);
    }

    public UserDeck updateUserDeck(UUID id, UserDeck userDeck) {
        User currentUser = UserUtils.getCurrentUser();
        UserDeck existingDeck = getUserDeckById(currentUser, id);
        userDeckValidator.validateUserDeckForUpdate(currentUser.getWorkplaceId(), userDeck);
        existingDeck.setName(userDeck.getName());
        existingDeck.setDescription(userDeck.getDescription());
        existingDeck.setAvailable(userDeck.isAvailable());
        return userDeckRepository.save(existingDeck);
    }

    public UserDeck getSingleDeck(UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        return getUserDeckById(currentUser, id);
    }

    public List<UserDeck> getAllDecks() {
        User currentUser = UserUtils.getCurrentUser();
        return userDeckRepository.findAllByWorkplaceId(currentUser.getWorkplaceId());
    }

    public Page<Card> getAllCardsInDeck(UUID deckId, PageableRequestQuery pageableRequestQuery) {
        User currentUser = UserUtils.getCurrentUser();
        List<Card> cards = getUserDeckById(currentUser, deckId).getCardList();
        Pageable pageable = pageableRequestQuery.getPageable();
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), cards.size());
        return new PageImpl<>(cards.subList(start, end), pageableRequestQuery.getPageable(), cards.size());
    }

    public List<Card> getAllCards(UUID deckId) {
        return userCardRepository.findAllByDeckId(deckId);
    }

    public void removeCardFromDeck(UUID deckId, UUID cardId) {
        User currentUser = UserUtils.getCurrentUser();
        UserDeck deck = getUserDeckById(currentUser, deckId);
        Card cardToDelete = getUserCardById(cardId);
        userCardRepository.delete(cardToDelete);
        minioService.removeCardImage(
                currentUser.getWorkplaceId().toString(),
                deck.getId().toString(),
                cardToDelete.getId().toString(),
                cardToDelete.getExtension()
        );
        deck.setCardsCount(deck.getCardsCount() - 1);
        userDeckRepository.save(deck);
    }

    @Transactional
    public void removeCardbackFromDeck(UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        UserDeck deck = getUserDeckById(currentUser, id);
        minioService.removeCardImage(
                currentUser.getWorkplaceId().toString(),
                deck.getId().toString(),
                deck.getCardBack(),
                deck.getCardBackExtension()
        );
        deck.setCardBack(null);
        deck.setCardBackExtension(null);
        userDeckRepository.save(deck);
    }

    public void deleteUserDeckAndAllCardsInside(UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        UserDeck deck = getUserDeckById(currentUser, id);
        minioService.removeDeckWithContent(currentUser.getWorkplaceId().toString(), deck.getId().toString());
        userDeckRepository.delete(deck);
    }

    private void uploadCardBack(UserDeck deck, String filename, String extension, MultipartFile file) {
        if (StringUtils.isNotBlank(deck.getCardBack())) {
            minioService.removeCardImage(
                    deck.getWorkplaceId().toString(),
                    deck.getId().toString(),
                    deck.getCardBack(),
                    deck.getCardBackExtension()
            );
        }
        try {
            minioService.addCardImage(
                    deck.getWorkplaceId().toString(),
                    deck.getId().toString(),
                    filename,
                    extension,
                    new ByteArrayInputStream(file.getBytes()),
                    file.getSize());
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }
    }

    private UserDeck getUserDeckById(User currentUser, UUID id) {
        UserDeck userDeck = userDeckRepository.findById(id).orElse(null);
        if (Objects.isNull(userDeck)) {
            throw ExceptionHelper.getNotFoundException(UserDeck.class);
        }
        if (!userDeckValidator.isUserHasAccessToWorkplace(currentUser.getWorkplaceId(), userDeck.getWorkplaceId())) {
            throw ExceptionHelper.getUserHasNoAccessException(User.class);
        }
        return userDeck;
    }

    private Card getUserCardById(UUID id) {
        return userCardRepository.findById(id).orElseGet(() -> {
            log.error("[Card Exception]: UserCard with id={} not found", id);
            throw ExceptionHelper.getNotFoundException(Card.class);
        });
    }

    private Specification<UserDeck> getSpecification(UUID workplaceId, String searchText) {
        Specification<UserDeck> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserDeck.Fields.workplaceId), workplaceId);
        specification.and(
                SpecificationUtils.getSearchSpecification(searchText, root ->
                        Arrays.asList(root.get(UserDeck.Fields.name), root.get(UserDeck.Fields.description))
                )
        );
        return specification;
    }

    private boolean isCardExistsInDeck(UUID deckId, UUID cardId) {
        return getUserCardById(cardId).getDeckId().equals(deckId);
    }

}
