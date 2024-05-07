package org.example.dev_ban_hang.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.CustomerDTO;
import org.example.dev_ban_hang.Entity.Customer;
import org.example.dev_ban_hang.Mapper.CustomerMapper;
import org.example.dev_ban_hang.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    public final CustomerRepository customerRepository;
    public final CustomerMapper customerMapper;

    //hien thi customer
    public List<CustomerDTO> ShowAllCustomers() {
        List<Customer> CustomerList = customerRepository.findAll();
        return customerMapper.toDto(CustomerList);
    }
    //thêm customer
    @Transactional
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }
    //Sửa customer
    @Transactional
    public CustomerDTO getCustomerById(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customerMapper.toDto(customer.get());
    }
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    //xóa customer
    @Transactional
    public CustomerDTO deleteCustomerById(int customerId) {
        // Kiểm tra xem khách hàng có tồn tại không
        CustomerDTO CustomerDTO = getCustomerById(customerId);
        customerRepository.deleteById(customerId);
        return CustomerDTO;
    }


}
