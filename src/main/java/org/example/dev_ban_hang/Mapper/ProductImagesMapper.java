package org.example.dev_ban_hang.Mapper;

import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.ProductImagesDTO;
import org.example.dev_ban_hang.Entity.ProductImages;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductImagesMapper implements EntityMapper<ProductImages , ProductImagesDTO> {
    @Override
    public ProductImages toEntity(ProductImagesDTO dto) {
        return ProductImages
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .url(dto.getUrl())
                .build();
    }

    @Override
    public ProductImagesDTO toDto(ProductImages entity) {
        return ProductImagesDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .url(entity.getUrl())
                .build();
    }

    @Override
    public List<ProductImages> toEntity(List<ProductImagesDTO> Dto) {
        return List.of();
    }

    @Override
    public List<ProductImagesDTO> toDto(List<ProductImages> entity) {
        List<ProductImagesDTO> dtos = new ArrayList<>();
        entity.forEach(ProductImages ->
                {
                    ProductImagesDTO productImagesDTO = toDto(ProductImages);
                    dtos.add(productImagesDTO);
                }
        );
        return dtos;
    }
}
