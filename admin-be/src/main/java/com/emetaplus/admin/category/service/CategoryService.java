package com.emetaplus.admin.category.service;

import com.emetaplus.admin.category.model.Category;
import com.emetaplus.admin.category.repository.CategoryRepository;
import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.core.query.PageableRequestQuery;
import com.emetaplus.admin.core.utils.SpecificationUtils;
import com.emetaplus.admin.role.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    public Category createCategory(Category category) {
        categoryValidator.validateCategoryForCreate(category);
        return categoryRepository.save(category);
    }

    public Category updateCategory(UUID id, Category category) {
        Category existingCategory = getCategoryById(id);
        categoryValidator.validateCategoryForUpdate(id, category);
        category.setId(existingCategory.getId());
        return categoryRepository.save(category);
    }

    public Category getCategory(UUID id) {
        return getCategoryById(id);
    }

    public Page<Category> getAllCategories(PageableRequestQuery pageableRequestQuery) {
        Specification<Category> specification = getSpecification(pageableRequestQuery.getSearchText());
        return categoryRepository.findAll(specification, pageableRequestQuery.getPageable());
    }

    public List<Category> getAllCategoriesForSelection() {
        return categoryRepository.findAll();
    }

    public Category getByIdentifier(String identifier) {
        return categoryRepository.findOneByIdentifier(identifier).orElseGet(() -> {
            log.error("[Category Exception]: Category with identifier={} not found", identifier);
            throw ExceptionHelper.getNotFoundException(Category.class);
        });
    }

    public void deleteCategory(UUID id) {
        Category category = getCategoryById(id);
        categoryValidator.validateCategoryForDelete(id);
        categoryRepository.delete(category);
    }

    @Transactional
    public void deleteCategoryAndAttachments(UUID id) {
        Category category = getCategoryById(id);
        categoryRepository.deleteCategoryAttachments(id);
        categoryRepository.delete(category);

    }

    private Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseGet(() -> {
            log.error("[Category Exception]: Category with id={} not found", id);
            throw ExceptionHelper.getNotFoundException(Category.class);
        });
    }

    private Specification<Category> getSpecification(String searchText) {
        return SpecificationUtils.getSearchSpecification(searchText, root ->
                Arrays.asList(
                        root.get(Category.Fields.name),
                        root.get(Category.Fields.description),
                        root.get(Category.Fields.identifier)
                )
        );
    }
}
