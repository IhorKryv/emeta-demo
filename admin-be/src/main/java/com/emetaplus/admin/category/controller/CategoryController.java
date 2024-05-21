package com.emetaplus.admin.category.controller;

import com.emetaplus.admin.category.dto.CategoryDTO;
import com.emetaplus.admin.category.mapper.CategoryMapper;
import com.emetaplus.admin.category.model.Category;
import com.emetaplus.admin.category.service.CategoryService;
import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.query.PageableRequestQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController {

    private static final String CREATE_CATEGORY_PATH = "create";
    private static final String UPDATE_CATEGORY_PATH = "update/{id}";
    private static final String GET_SINGLE_CATEGORY_PATH = "get/{id}";
    private static final String GET_ALL_CATEGORY_FOR_SELECTION_PATH = "get/selection";
    private static final String GET_ALL_CATEGORY_PATH = "get/all";
    private static final String DELETE_CATEGORY_PATH = "delete/{id}";
    private static final String DELETE_CATEGORY_AND_ATTACHMENTS_PATH = "delete/{id}/attachments";

    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    @PostMapping(CREATE_CATEGORY_PATH)
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return mapper.toDTO(categoryService.createCategory(mapper.toEntity(categoryDTO)));
    }

    @PutMapping(UPDATE_CATEGORY_PATH)
    public CategoryDTO updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO) {
        return mapper.toDTO(categoryService.updateCategory(id, mapper.toEntity(categoryDTO)));
    }

    @GetMapping(GET_SINGLE_CATEGORY_PATH)
    public CategoryDTO getCategory(@PathVariable UUID id) {
        return mapper.toDTO(categoryService.getCategory(id));
    }

    @GetMapping(GET_ALL_CATEGORY_PATH)
    public PageDTO<CategoryDTO> getAllCategories(PageableRequestQuery pageableRequestQuery) {
        return mapper.toCategoryPageDTO(categoryService.getAllCategories(pageableRequestQuery));
    }

    @GetMapping(GET_ALL_CATEGORY_FOR_SELECTION_PATH)
    public List<CategoryDTO> getAllCategories() {
        return mapper.toCategoryListDTO(categoryService.getAllCategoriesForSelection());
    }

    @DeleteMapping(DELETE_CATEGORY_PATH)
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

    @DeleteMapping(DELETE_CATEGORY_AND_ATTACHMENTS_PATH)
    public void deleteCategoryAndAttachments(@PathVariable UUID id) {
        categoryService.deleteCategoryAndAttachments(id);
    }
}
