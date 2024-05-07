package org.example.dev_ban_hang.Mapper;

import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.TransportMethodDTO;
import org.example.dev_ban_hang.Entity.TransportMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransportMethodMapper implements EntityMapper<TransportMethod, TransportMethodDTO> {

    @Override
    public TransportMethod toEntity(TransportMethodDTO dto) {
        return TransportMethod.builder()
                .id(dto.getId())
                .name(dto.getName())
                .notes(dto.getNotes())
                .createdDate(dto.getCreatedDate())
                .updatedDate(dto.getUpdatedDate())
                .isactive(dto.getIsactive())
                .build();
    }

    @Override
    public TransportMethodDTO toDto(TransportMethod entity) {
        return TransportMethodDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .notes(entity.getNotes())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .isactive(entity.getIsactive())
                .build();
    }

    @Override
    public List<TransportMethod> toEntity(List<TransportMethodDTO> dtos) {
        List<TransportMethod> entities = new ArrayList<>();
        dtos.forEach(dto -> entities.add(toEntity(dto)));
        return entities;
    }

    @Override
    public List<TransportMethodDTO> toDto(List<TransportMethod> entity) {
        List<TransportMethodDTO> dtos = new ArrayList<>();
        entity.forEach(TransportMethod ->
                {
                    TransportMethodDTO transportMethodDTO = toDto(TransportMethod);
                    dtos.add(transportMethodDTO);
                }
        );
        return dtos;
    }
}
