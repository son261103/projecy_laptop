package org.example.dev_ban_hang.Mapper;

import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.CustomerDTO;
import org.example.dev_ban_hang.Entity.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerMapper implements EntityMapper<Customer, CustomerDTO> {

    @Override
    public Customer toEntity(CustomerDTO dto) {
        return Customer
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .createdDate(dto.getCreatedDate())
                .isactive(dto.getIsactive())
                .build();
    }

    @Override
    public CustomerDTO toDto(Customer entity) {
        return CustomerDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .address(entity.getAddress())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .createdDate(entity.getCreatedDate())
                .isactive(entity.getIsactive())
                .build();
    }

    @Override
    public List<Customer> toEntity(List<CustomerDTO> Dto) {
        return List.of();
    }

    @Override
    public List<CustomerDTO> toDto(List<Customer> entity) {
        List<CustomerDTO> dtos = new ArrayList<>();
        entity.forEach(Customer ->
                {
                    CustomerDTO customerDTO = toDto(Customer);
                    dtos.add(customerDTO);
                }
        );
        return dtos;
    }
}
