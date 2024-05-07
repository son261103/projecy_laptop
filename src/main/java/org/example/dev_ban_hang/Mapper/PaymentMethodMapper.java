package org.example.dev_ban_hang.Mapper;

import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.PaymentMethodDTO;
import org.example.dev_ban_hang.Entity.PaymentMethod;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentMethodMapper implements EntityMapper<PaymentMethod, PaymentMethodDTO> {

    @Override
    public PaymentMethod toEntity(PaymentMethodDTO dto) {
        return PaymentMethod.builder()
                .id(dto.getId())
                .name(dto.getName())
                .notes(dto.getNotes())
                .createdDate(dto.getCreatedDate())
                .updatedDate(dto.getUpdatedDate())
                .isactive(dto.getIsactive())
                .build();
    }

    @Override
    public PaymentMethodDTO toDto(PaymentMethod entity) {
        return PaymentMethodDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .notes(entity.getNotes())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .isactive(entity.getIsactive())
                .build();
    }

    @Override
    public List<PaymentMethod> toEntity(List<PaymentMethodDTO> Dto) {
        return List.of();
    }

    @Override
    public List<PaymentMethodDTO> toDto(List<PaymentMethod> entity) {
        List<PaymentMethodDTO> dtos = new ArrayList<>();
        entity.forEach(Paymentmethod ->
                {
                    PaymentMethodDTO paymentMethodDTO = toDto(Paymentmethod);
                    dtos.add(paymentMethodDTO);
                }
        );
        return dtos;
    }
}
