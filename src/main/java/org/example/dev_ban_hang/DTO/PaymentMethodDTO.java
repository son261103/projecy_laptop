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
public class PaymentMethodDTO {
    private int id;
    private String name;
    private String notes;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isactive;
}
