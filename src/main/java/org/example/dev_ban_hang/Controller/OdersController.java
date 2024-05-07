package org.example.dev_ban_hang.Controller;


import org.example.dev_ban_hang.DTO.CustomerDTO;
import org.example.dev_ban_hang.DTO.OrdersDTO;
import org.example.dev_ban_hang.Service.CustomerService;
import org.example.dev_ban_hang.Service.OdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class OdersController {
    @Autowired
    private OdersService ordersService;
    @Autowired
    public CustomerService customerService;

    @GetMapping("/show_orders")
    public String showOrders(Model model) {
        List<OrdersDTO> ordersDTOS = ordersService.ShowAllOrders();
        model.addAttribute("orders", ordersDTOS); // Thay vì 'oders', sử dụng 'orders'
        return "Layout/Oders/Oders";
    }

    // Hiển thị form thêm đơn hàng
    @GetMapping("/add_orders/{customer_Id}")
    public String showAddOrderForm(@PathVariable("customer_Id") int customerId, Model model) {
        OrdersDTO orderDTO = new OrdersDTO();
        orderDTO.setOrdersDate(LocalDateTime.now()); // Set ngày hiện tại
        model.addAttribute("customerId", customerId);
        // Lấy thông tin của khách hàng dựa trên customerId và truyền vào model
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customerDTO);
        model.addAttribute("customerName", customerDTO.getName()); // Thêm tên khách hàng vào model
        model.addAttribute("orderDTO", orderDTO);

        return "Layout/Oders/add_orders";
    }

    // Xử lý thêm đơn hàng
    @PostMapping("/add_order/{customer_id}")
    public String addOrder(@ModelAttribute("orderDTO") OrdersDTO ordersDTO, @PathVariable("customer_id") int customerId) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        ordersDTO.setIdCustomer(customerDTO);

        // Set ngày hiện tại
        ordersDTO.setOrdersDate(LocalDateTime.now());

        ordersService.addOrder(ordersDTO);

        return "redirect:/show_orders";
    }

    // sửa oders
    @GetMapping("/edit_order/{orderId}/{customerId}")
    public String showEditOrderForm(@PathVariable("orderId") int orderId, @PathVariable("customerId") int customerId, Model model) {
        OrdersDTO orderDTO = ordersService.getOrderById(orderId);
        if (orderDTO != null) {
            CustomerDTO customerDTO = customerService.getCustomerById(customerId);
            model.addAttribute("orderDTO", orderDTO);
            model.addAttribute("customerId", customerId);
            model.addAttribute("customer", customerDTO);
            model.addAttribute("customerName", customerDTO.getName());
            return "Layout/Oders/edit_order";
        } else {
            return "redirect:/show_orders";
        }
    }



    @PostMapping("/edit_order/{orderId}/{customerId}")
    public String editOrder(@ModelAttribute("orderDTO") OrdersDTO ordersDTO, @PathVariable("orderId") int orderId, @RequestParam("customerId") String customerId) {
        // Convert customerId to CustomerDTO
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(Integer.parseInt(customerId)); // Assuming the ID is an integer
        ordersDTO.setIdCustomer(customerDTO);

        // Update the order
        ordersService.updateOrder(ordersDTO);

        return "redirect:/show_orders";
    }

    //xóa orders
    @DeleteMapping("/delete_order/{orderId}")
    public ResponseEntity<String> deleteOrdersById(@PathVariable int orderId) {
        try {
            ordersService.deleteOrdersById(orderId);
            return ResponseEntity.ok("orders deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete orders");
        }
    }




}
