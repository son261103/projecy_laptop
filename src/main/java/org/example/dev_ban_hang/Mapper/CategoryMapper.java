package org.example.dev_ban_hang.Mapper;

import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.CategoryDTO;
import org.example.dev_ban_hang.Entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper implements EntityMapper<Category, CategoryDTO> {

    @Override
    public Category toEntity(CategoryDTO dto) {
        return Category
                .builder()
                .id(dto.getId())
                .idparent(dto.getIdparent())
                .name(dto.getName())
                .notes(dto.getNotes())
                .icon(dto.getIcon())
                .createdDate(dto.getCreatedDate())
                .updatedDate(dto.getUpdatedDate())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .isactive(dto.getIsactive())
                .build();
    }

    @Override
    public CategoryDTO toDto(Category entity) {
        return CategoryDTO
                .builder()
                .id(entity.getId())
                .idparent(entity.getIdparent())
                .name(entity.getName())
                .notes(entity.getNotes())
                .icon(entity.getIcon())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .isactive(entity.getIsactive())
                .build();
    }

    @Override
    public List<Category> toEntity(List<CategoryDTO> Dto) {
        return List.of();
    }

    @Override
    public List<CategoryDTO> toDto(List<Category> entity) {
        List<CategoryDTO> dtos = new ArrayList<>();
        entity.forEach(Category ->
                {
                    CategoryDTO categoryDTO = toDto(Category);
                    dtos.add(categoryDTO);
                }
        );
        return dtos;
    }
}
