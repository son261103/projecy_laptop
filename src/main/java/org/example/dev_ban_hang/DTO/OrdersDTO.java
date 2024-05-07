package org.example.dev_ban_hang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private int id;
    private String idorders;
    private LocalDateTime ordersDate;
    private CustomerDTO idCustomer;
    private Double totalMoney;
    private String notes;
    private String nameReciver;
    private String address;
    private String phone;
    private String customerName;
}
