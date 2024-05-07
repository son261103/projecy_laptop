package org.example.dev_ban_hang.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.PaymentMethodDTO;
import org.example.dev_ban_hang.DTO.TransportMethodDTO;
import org.example.dev_ban_hang.Entity.PaymentMethod;
import org.example.dev_ban_hang.Mapper.PaymentMethodMapper;
import org.example.dev_ban_hang.Repository.PaymentMethodRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    // Hiển thị danh sách phương thức thanh toán
    public List<PaymentMethodDTO> showAllPaymenttMethods() {
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        return paymentMethodMapper.toDto(paymentMethodList);
    }

    //thêm payment
    @Transactional
    public PaymentMethodDTO addPayment(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(paymentMethodDTO);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.toDto(paymentMethod);
    }

    //edit payment
    @Transactional
    public PaymentMethodDTO getPaymentById(int id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);
        return paymentMethodMapper.toDto(paymentMethod.get());
    }

    public PaymentMethodDTO updatePayment(PaymentMethodDTO paymentMethodDTO) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(paymentMethodDTO.getId());
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            // Lấy giá trị của createdDate từ đối tượng cũ
            LocalDateTime createdDate = paymentMethod.getCreatedDate();

            // Gán giá trị createdDate vào đối tượng mới trước khi cập nhật
            paymentMethodDTO.setCreatedDate(createdDate);

            // Cập nhật thông tin và trả về
            paymentMethod = paymentMethodMapper.toEntity(paymentMethodDTO);
            paymentMethod = paymentMethodRepository.save(paymentMethod);
            return paymentMethodMapper.toDto(paymentMethod);
        } else {
            return null;
        }
    }


    //delete payment
    @Transactional
    public PaymentMethodDTO deletePaymentransportById(int paymentId) {
        // Kiểm tra xem khách hàng có tồn tại không
        PaymentMethodDTO paymentMethodDTO = getPaymentById(paymentId);
        paymentMethodRepository.deleteById(paymentId);
        return paymentMethodDTO;
    }
}
