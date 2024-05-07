package org.example.dev_ban_hang.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.TransportMethodDTO;
import org.example.dev_ban_hang.Entity.TransportMethod;
import org.example.dev_ban_hang.Mapper.TransportMethodMapper;
import org.example.dev_ban_hang.Repository.TransportMethodRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor
public class TransportMethodService {
    private final TransportMethodRepository transportMethodRepository;
    private final TransportMethodMapper transportMethodMapper;

    //hiển thị transport
    public List<TransportMethodDTO> showAllTransportMethods() {
        List<TransportMethod> transportMethodList = transportMethodRepository.findAll();
        return transportMethodMapper.toDto(transportMethodList);
    }

    //thêm transport
    @Transactional
    public TransportMethodDTO addTransport(TransportMethodDTO transportMethodDTO) {
        TransportMethod transportMethod = transportMethodMapper.toEntity(transportMethodDTO);
        transportMethod = transportMethodRepository.save(transportMethod);
        return transportMethodMapper.toDto(transportMethod);
    }

    //edit transport
    @Transactional
    public TransportMethodDTO getTransportById(int id) {
        Optional<TransportMethod> transportMethod = transportMethodRepository.findById(id);
        return transportMethodMapper.toDto(transportMethod.get());
    }

    public TransportMethodDTO updateTransport(TransportMethodDTO transportMethodDTO) {
        Optional<TransportMethod> transportMethodOptional = transportMethodRepository.findById(transportMethodDTO.getId());
        if (transportMethodOptional.isPresent()) {
            TransportMethod transportMethod = transportMethodOptional.get();
            // Lấy giá trị của createdDate từ đối tượng cũ
            LocalDateTime createdDate = transportMethod.getCreatedDate();

            // Gán giá trị createdDate vào đối tượng mới trước khi cập nhật
            transportMethodDTO.setCreatedDate(createdDate);

            // Cập nhật thông tin và trả về
            transportMethod = transportMethodMapper.toEntity(transportMethodDTO);
            transportMethod = transportMethodRepository.save(transportMethod);
            return transportMethodMapper.toDto(transportMethod);
        } else {
            // Xử lý trường hợp không tìm thấy transport
            // ở đây có thể ném một exception hoặc xử lý theo ý muốn của bạn
            return null;
        }
    }


    //delete transport
    @Transactional
    public TransportMethodDTO deleteTransportById(int transportId) {
        // Kiểm tra xem khách hàng có tồn tại không
        TransportMethodDTO transportMethodDTO = getTransportById(transportId);
        transportMethodRepository.deleteById(transportId);
        return transportMethodDTO;
    }
}
