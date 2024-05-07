package org.example.dev_ban_hang.Controller;


import org.example.dev_ban_hang.DTO.CustomerDTO;
import org.example.dev_ban_hang.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/show_customer")
    public String showCustomers(Model model) {
        List<CustomerDTO> customerDTOs = customerService.ShowAllCustomers();
        model.addAttribute("customers", customerDTOs);
        return "Layout/Customer/Customer";
    }

    //thêm customer
    @GetMapping("/crud_customer")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "Layout/Customer/Crud_Customer";
    }

    @PostMapping("/add_customer")
    public String crudCustomer(@ModelAttribute("customerDTO") CustomerDTO customerDTO) {
        customerDTO.setCreatedDate(LocalDateTime.now());
        customerService.addCustomer(customerDTO);
        return "redirect:/show_customer";
    }

    //sửa customer
    // Hiển thị biểu mẫu chỉnh sửa thông tin khách hàng
    @GetMapping("/edit_customer/{id}")
    public String showEditCustomerForm(@PathVariable("id") int id, Model model) {
        // Lấy thông tin của khách hàng từ service
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        model.addAttribute("customerDTO", customerDTO);
        return "Layout/Customer/EditCustomer";
    }

    // Xử lý yêu cầu chỉnh sửa thông tin khách hàng
    @PostMapping("/edit_customer/{id}")
    public String editCustomer(@PathVariable("id") int id, @ModelAttribute("customerDTO") CustomerDTO customerDTO) {
        // Cập nhật thông tin của khách hàng
        customerDTO.setId(id); // Đảm bảo rằng id của khách hàng không bị thay đổi
        customerDTO.setCreatedDate(LocalDate.now().atStartOfDay());
        customerService.updateCustomer(customerDTO);
        return "redirect:/show_customer";
    }

    //xóa customer
// Phương thức xóa khách hàng
    @DeleteMapping("/delete_customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
        try {
            customerService.deleteCustomerById(customerId);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (Exception e) {
            // Xử lý trường hợp xóa không thành công
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete customer");
        }
    }
}
