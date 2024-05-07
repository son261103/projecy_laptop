package org.example.dev_ban_hang.Repository;

import org.example.dev_ban_hang.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface OdersRepository extends JpaRepository<Orders, Integer> {

    // Đếm số lượng đơn hàng trong ngày hiện tại
    Long countByOrdersDate(LocalDateTime ordersDate);
}
