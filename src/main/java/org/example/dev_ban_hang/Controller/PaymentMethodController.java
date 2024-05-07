package org.example.dev_ban_hang.Controller;

import org.example.dev_ban_hang.DTO.PaymentMethodDTO;
import org.example.dev_ban_hang.DTO.TransportMethodDTO;
import org.example.dev_ban_hang.Service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    //hiển thị payment
    @GetMapping("/show_payment")
    public String showPayment(Model model) {
        List<PaymentMethodDTO> paymentMethodDTOS = paymentMethodService.showAllPaymenttMethods();
        model.addAttribute("paymentMethodDTOS", paymentMethodDTOS);
        return "Layout/Payment/Payment";
    }

    //thêm payment
    @GetMapping("/add_payment")
    public String showAddPaymentForm(Model model) {
        model.addAttribute("paymentMethodDTOS", new PaymentMethodDTO());
        return "Layout/Payment/add_payment";
    }

    @PostMapping("/add_payment_s")
    public String addPayment(@ModelAttribute(" paymentMethodDTOS ") PaymentMethodDTO paymentMethodDTO) {
        paymentMethodDTO.setCreatedDate(LocalDateTime.now());
        paymentMethodDTO.setUpdatedDate(LocalDateTime.now());
        paymentMethodService.addPayment(paymentMethodDTO);
        return "redirect:/show_payment";
    }

    // Hiển thị form edit payment
    @GetMapping("/edit_payment/{id}")
    public String showEditPaymentForm(@PathVariable("id") int id, Model model) {
        PaymentMethodDTO paymentMethodDTO = paymentMethodService.getPaymentById(id);
        model.addAttribute("paymentMethodDTO", paymentMethodDTO);
        return "Layout/Payment/edit_payment";
    }


    // Xử lý yêu cầu chỉnh sửa thông tin transport
    @PostMapping("/edit_payment/{id}")
    public String editPayment(@PathVariable("id") int id, @ModelAttribute("paymentMethodDTO") PaymentMethodDTO paymentMethodDTO) {
        paymentMethodDTO.setId(id);
        paymentMethodDTO.setUpdatedDate(LocalDateTime.now());
        paymentMethodService.updatePayment(paymentMethodDTO);
        return "redirect:/show_payment";
    }


    //xóa transport
    @DeleteMapping("/delete_payment/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable int paymentId) {
        try {
            paymentMethodService.deletePaymentransportById(paymentId);
            return ResponseEntity.ok("payment deleted successfully");
        } catch (Exception e) {
            // Xử lý trường hợp xóa không thành công
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete payment");
        }
    }
}
