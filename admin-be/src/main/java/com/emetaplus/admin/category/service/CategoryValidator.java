package com.emetaplus.admin.category.service;

import com.emetaplus.admin.category.model.Category;
import com.emetaplus.admin.category.repository.CategoryRepository;
import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.role.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public void validateCategoryForCreate(Category category) {
        validateName(null, category.getName(), "create");
        validateIdentifier(null, category.getIdentifier(), "create");
    }

    public void validateCategoryForUpdate(UUID id, Category category) {
        validateName(id, category.getName(), "update");
        validateIdentifier(id, category.getIdentifier(), "update");
    }

    public void validateCategoryForDelete(UUID id) {
        if (categoryRepository.getDecksCountWithCurrentCategory(id) > 0) {
            log.error("[Category Exception]: Category can't be deleted, because one or few decks have this category");
            throw ExceptionHelper.getCannotDeleteException(Category.class);
        }
    }

    private void validateName(UUID id, String name, String method) {
        if (StringUtils.isBlank(name.trim())) {
            log.error("[Category Exception]: Can't {} category without name", method);
            throw ExceptionHelper.getInvalidFieldException(Category.class, "name");
        }

        if (isCategoryWithNameAlreadyExists(id, name)) {
            log.error("[Category Exception]: Category with name={} already exists", name);
            throw ExceptionHelper.getAlreadyExistsException(Category.class, "name");
        }
    }

    private void validateIdentifier(UUID id, String identifier, String method) {
        if (StringUtils.isBlank(identifier)) {
            log.error("[Category Exception]: Can't {} category without unique identifier", identifier);
            throw ExceptionHelper.getInvalidFieldException(Category.class, "identifier");
        }

        if (!isIdentifierValid(identifier)) {
            log.error("[Category Exception]: Can't {} with invalid identifier", method);
            throw ExceptionHelper.getInvalidFieldException(Category.class, "identifier");
        }

        if (isCategoryWithIdentifierAlreadyExists(id, identifier)) {
            log.error("[Category Exception]: Category with identifier={} already exists", identifier);
            throw ExceptionHelper.getAlreadyExistsException(Category.class, "identifier");
        }


    }

    private boolean isCategoryWithNameAlreadyExists(UUID id, String name) {
        Category category = categoryRepository.findOneByName(name).orElse(null);
        if (Objects.isNull(category)) {
            return false;
        }
        return !category.getId().equals(id);
    }

    private boolean isCategoryWithIdentifierAlreadyExists(UUID id, String identifier) {
        Category category = categoryRepository.findOneByIdentifier(identifier).orElse(null);
        if (Objects.isNull(category)) {
            return false;
        }
        return !category.getId().equals(id);
    }

    private boolean isIdentifierValid(String identifier) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(identifier);
        return matcher.matches();
    }
}
