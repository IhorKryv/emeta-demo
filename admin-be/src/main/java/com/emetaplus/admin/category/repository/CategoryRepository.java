package com.emetaplus.admin.category.repository;

import com.emetaplus.admin.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {
    Optional<Category> findOneByName(String name);

    Optional<Category> findOneByIdentifier(String identifier);

    @Query(value = "SELECT COUNT(*) FROM decks_categories where category_id = :categoryId", nativeQuery = true)
    Long getDecksCountWithCurrentCategory(UUID categoryId);

    @Modifying
    @Query(value = "DELETE FROM decks_categories where category_id = :categoryId", nativeQuery = true)
    void deleteCategoryAttachments(UUID categoryId);

}
