package org.example.dev_ban_hang.Mapper;


import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.CategoryDTO;
import org.example.dev_ban_hang.DTO.ProductDTO;
import org.example.dev_ban_hang.Entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper implements EntityMapper<Product, ProductDTO> {

    @Override
    public Product toEntity(ProductDTO dto) {
        return Product
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .notes(dto.getNotes())
                .image(dto.getImage())
                .idcategory(dto.getIdcategory())
                .price(dto.getPrice())
                .quatity(dto.getQuantity())
                .createdDate(dto.getCreatedDate())
                .updatedDate(dto.getUpdatedDate())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .isactive(dto.getIsActive())
                .build();
    }

    @Override
    public ProductDTO toDto(Product entity) {
        return ProductDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .notes(entity.getNotes())
                .image(entity.getImage())
                .idcategory(entity.getIdcategory())
                .price(entity.getPrice())
                .quantity(entity.getQuatity())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .isActive(entity.getIsactive())
                .build();
    }

    @Override
    public List<Product> toEntity(List<ProductDTO> Dto) {
        return List.of();
    }

    @Override
    public List<ProductDTO> toDto(List<Product> entity) {
        List<ProductDTO> dtos = new ArrayList<>();
        entity.forEach(Product ->
                {
                    ProductDTO productDTO = toDto(Product);
                    dtos.add(productDTO);
                }
        );
        return dtos;
    }
}
