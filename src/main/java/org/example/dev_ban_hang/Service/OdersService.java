package org.example.dev_ban_hang.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.CategoryDTO;
import org.example.dev_ban_hang.DTO.CustomerDTO;
import org.example.dev_ban_hang.DTO.OrdersDTO;
import org.example.dev_ban_hang.DTO.ProductDTO;
import org.example.dev_ban_hang.Entity.Category;
import org.example.dev_ban_hang.Entity.Customer;
import org.example.dev_ban_hang.Entity.Orders;
import org.example.dev_ban_hang.Entity.Product;
import org.example.dev_ban_hang.Mapper.CustomerMapper;
import org.example.dev_ban_hang.Mapper.OrdersMapper;
import org.example.dev_ban_hang.Repository.CustomerRepository;
import org.example.dev_ban_hang.Repository.OdersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Component
@RequiredArgsConstructor
public class OdersService {
    private final OdersRepository odersRepository;
    private final OrdersMapper ordersMapper;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    //hien thi Oders
    public List<OrdersDTO> ShowAllOrders() {
        List<Orders> ordersList = odersRepository.findAll();
        List<OrdersDTO> ordersDTOList = ordersMapper.toDto(ordersList);

        // Lặp qua từng đơn hàng để lấy thông tin khách hàng từ ID khách hàng
        for (OrdersDTO ordersDTO : ordersDTOList) {
            int customerId = ordersDTO.getIdCustomer().getId(); // Sử dụng getCustomerId() thay vì getIdCustomer().getId()
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer != null) {
                ordersDTO.setCustomerName(customer.getName());
            }
        }
        return ordersDTOList; // Trả về danh sách đã được cập nhật thông tin khách hàng
    }



    //add orders
    // Lấy danh sách tất cả khách hàng
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.toDto(customers);
    }

    // Thêm đơn hàng mới
    @Transactional
    public OrdersDTO addOrder(OrdersDTO ordersDTO) {
        // Tạo ID cho đơn hàng theo định dạng yyyymmddxxx
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long countOrders = odersRepository.countByOrdersDate(LocalDateTime.now());
        String orderId = currentDate + String.format("%04d", countOrders + 1); // Định dạng số thứ tự có 3 chữ số, bắt đầu từ 001
        // Gán ID mới cho đơn hàng
        ordersDTO.setIdorders(orderId);
        // Chuyển đổi DTO thành Entity
        Orders order = ordersMapper.toEntity(ordersDTO);
        // Lưu đơn hàng vào cơ sở dữ liệu
        order = odersRepository.save(order);
        // Chuyển đổi Entity đã lưu thành DTO và trả về
        return ordersMapper.toDto(order);
    }


    ///sửa odders
    // Lấy thông tin đơn hàng dựa vào orderId
    public OrdersDTO getOrderById(int orderId) {
        Orders order = odersRepository.findById(orderId).orElse(null);
        if (order != null) {
            return ordersMapper.toDto(order);
        } else {
            return null;
        }
    }

    // Cập nhật thông tin đơn hàng
    public void updateOrder(OrdersDTO ordersDTO) {
        // Chuyển đổi DTO thành Entity
        Orders order = ordersMapper.toEntity(ordersDTO);
        // Lưu thông tin đơn hàng đã cập nhật vào cơ sở dữ liệu
        odersRepository.save(order);
    }

    // Xóa đơn hàng
    @Transactional
    public OrdersDTO deleteOrdersById(int orderId) {
        // Kiểm tra xem khách hàng có tồn tại không
        OrdersDTO ordersDTO = getOrderById(orderId);
        odersRepository.deleteById(orderId);
        return ordersDTO;
    }
}
