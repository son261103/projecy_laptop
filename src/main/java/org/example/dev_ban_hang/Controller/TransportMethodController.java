package org.example.dev_ban_hang.Controller;

import org.example.dev_ban_hang.DTO.CustomerDTO;
import org.example.dev_ban_hang.DTO.ProductDTO;
import org.example.dev_ban_hang.DTO.TransportMethodDTO;
import org.example.dev_ban_hang.Service.TransportMethodService;
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
public class TransportMethodController {
    @Autowired
    private TransportMethodService transportMethodService;

    //hiển thị transport
    @GetMapping("/show_transport")
    public String showTransport(Model model) {
        List<TransportMethodDTO> transportMethodDTOS = transportMethodService.showAllTransportMethods();
        model.addAttribute("transportMethodDTOS", transportMethodDTOS);
        return "Layout/Transport/Transport";
    }

    //thêm transport
    @GetMapping("/add_transport")
    public String showAddTransportForm(Model model) {
        model.addAttribute("transportMethodDTO", new TransportMethodDTO());
        return "Layout/Transport/add_transport";
    }

    @PostMapping("/add_transport_s")
    public String addTransport(@ModelAttribute("transportMethodDTO  ") TransportMethodDTO transportMethodDTO) {
        transportMethodDTO.setCreatedDate(LocalDateTime.now());
        transportMethodDTO.setUpdatedDate(LocalDateTime.now());
        transportMethodService.addTransport(transportMethodDTO);
        return "redirect:/show_transport";
    }

    // Hiển thị form edit transport
    @GetMapping("/edit_transport/{id}")
    public String showEditTransportForm(@PathVariable("id") int id, Model model) {
        // Lấy thông tin của khách hàng từ service
        TransportMethodDTO transportMethodDTO = transportMethodService.getTransportById(id);
        model.addAttribute("transportMethodDTO", transportMethodDTO);
        return "Layout/Transport/edit_transport";
    }

    // Xử lý yêu cầu chỉnh sửa thông tin transport
    @PostMapping("/edit_transport/{id}")
    public String editTransport(@PathVariable("id") int id, @ModelAttribute("transportMethodDTO") TransportMethodDTO transportMethodDTO) {
        // Cập nhật thông tin của khách hàng
        transportMethodDTO.setId(id); // Đảm bảo rằng id của khách hàng không bị thay đổi
        transportMethodDTO.setUpdatedDate(LocalDateTime.now());
        transportMethodService.updateTransport(transportMethodDTO);
        return "redirect:/show_transport";
    }

    //xóa transport
    @DeleteMapping("/delete_transport/{transportId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int transportId) {
        try {
            transportMethodService.deleteTransportById(transportId);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (Exception e) {
            // Xử lý trường hợp xóa không thành công
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete customer");
        }
    }
}
