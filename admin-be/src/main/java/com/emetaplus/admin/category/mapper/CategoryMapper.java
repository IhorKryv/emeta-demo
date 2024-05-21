package com.emetaplus.admin.category.mapper;

import com.emetaplus.admin.category.dto.CategoryDTO;
import com.emetaplus.admin.category.model.Category;
import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.mapper.AbstractMapperFactory;
import com.emetaplus.admin.core.mapper.MappingInstance;
import com.emetaplus.admin.core.mapper.MappingInstancesBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper extends AbstractMapperFactory {

    protected CategoryMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Category, CategoryDTO> categoryInstance = new MappingInstance<>(Category.class, CategoryDTO.class);
        builder
                .add(categoryInstance);
    }

    public Category toEntity(CategoryDTO dto) {
        return getMapper(Category.class, CategoryDTO.class).toEntity(dto);
    }

    public CategoryDTO toDTO(Category category) {
        return getMapper(Category.class, CategoryDTO.class).toDTO(category);
    }

    public PageDTO<CategoryDTO> toCategoryPageDTO(Page<Category> categories) {
        return getMapper(Category.class, CategoryDTO.class).toDTO(categories);
    }

    public List<CategoryDTO> toCategoryListDTO(List<Category> categories) {
        return getMapper(Category.class, CategoryDTO.class).toDTO(categories);
    }
}
