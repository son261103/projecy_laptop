package org.example.dev_ban_hang.Mapper;

import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.CustomerDTO;
import org.example.dev_ban_hang.DTO.OrdersDTO;
import org.example.dev_ban_hang.DTO.ProductDTO;
import org.example.dev_ban_hang.Entity.Customer;
import org.example.dev_ban_hang.Entity.Orders;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrdersMapper implements EntityMapper<Orders, OrdersDTO> {

    @Override
    public Orders toEntity(OrdersDTO dto) {
        return Orders.builder()
                .id(dto.getId())
                .idorders(dto.getIdorders())
                .ordersDate(dto.getOrdersDate())
                .idCustomer(Customer.builder().id(dto.getIdCustomer().getId()).build()) // Sử dụng id của CustomerDTO để tạo một đối tượng Customer
                .totalMoney(dto.getTotalMoney())
                .notes(dto.getNotes())
                .nameReciver(dto.getNameReciver())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();
    }

    @Override
    public OrdersDTO toDto(Orders entity) {
        return OrdersDTO.builder()
                .id(entity.getId())
                .idorders(entity.getIdorders())
                .ordersDate(entity.getOrdersDate())
                .idCustomer(CustomerDTO.builder().id(entity.getIdCustomer().getId()).build()) // Sử dụng id của CustomerDTO để tạo một đối tượng Customer
                .totalMoney(entity.getTotalMoney())
                .notes(entity.getNotes())
                .nameReciver(entity.getNameReciver())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .build();
    }

    @Override
    public List<Orders> toEntity(List<OrdersDTO> Dto) {
        return List.of();
    }

    @Override
    public List<OrdersDTO> toDto(List<Orders> entity) {
        List<OrdersDTO> dtos = new ArrayList<>();
        entity.forEach(Orders ->
                {
                    OrdersDTO ordersDTO = toDto(Orders);
                    dtos.add(ordersDTO);
                }
        );
        return dtos;
    }
}
