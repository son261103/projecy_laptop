package org.example.dev_ban_hang.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.CategoryDTO;
import org.example.dev_ban_hang.DTO.ProductDTO;
import org.example.dev_ban_hang.Entity.Category;
import org.example.dev_ban_hang.Entity.Product;
import org.example.dev_ban_hang.Mapper.CategoryMapper;
import org.example.dev_ban_hang.Mapper.ProductMapper;
import org.example.dev_ban_hang.Repository.CategoryRepository;
import org.example.dev_ban_hang.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    //hien thi product
    public List<ProductDTO> ShowAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<Category> categoryList = categoryRepository.findAll();
        return productMapper.toDto(productList);
    }

    //thêm product
    public String getUsername() {
        // Lấy đối tượng Authentication từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Khởi tạo tên người dùng với giá trị mặc định
        String username;

        // Kiểm tra xem Authentication có null không
        if (authentication != null) {
            // Lấy tên người dùng từ Authentication
            username = authentication.getName();
        } else {
            // Sử dụng giá trị mặc định nếu không có Authentication
            username = "Anonymous"; // Hoặc bất kỳ giá trị mặc định nào bạn muốn
        }

        return username;
    }


    // Lấy danh sách các danh mục
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDto(categories);
    }


    @Value("${image_product.upload.dir}")
    private String uploadDir;
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO, MultipartFile file) {
        try {
            // Kiểm tra xem có tệp ảnh được tải lên không
            if (file != null && !file.isEmpty()) {
                // Lưu trữ tệp ảnh vào thư mục uploadDir
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                File uploadPath = new File(uploadDir);
                File targetFile = new File(uploadPath, fileName);

                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    fos.write(file.getBytes());
                }

                // Lưu đường dẫn vào cột ICON của category
                String iconPath = "/images/images_product/" + fileName; // Đường dẫn cơ bản tới thư mục lưu trữ ảnh
                productDTO.setImage(iconPath);
            }
            // Chuyển đổi CategoryDTO thành Category entity
            Product product = productMapper.toEntity(productDTO);
            String username = getUsername();
            productDTO.setCreatedBy(username);
            productDTO.setUpdatedBy(username);
            productDTO.setCreatedDate(LocalDateTime.now());
            productDTO.setUpdatedDate(LocalDateTime.now());
            // Lưu trữ category vào cơ sở dữ liệu
            product = productRepository.save(product);
            // Chuyển đổi Category entity thành CategoryDTO và trả về
            return productMapper.toDto(product);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add product", e);
        }
    }


    //edit product
    @Transactional
    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục với ID: " + id));

        // Chuyển đổi Category thành CategoryDTO
        ProductDTO productDTO = productMapper.toDto(product);
        return productDTO;
    }


    @Transactional
    public void updateProductId(ProductDTO productDTO, MultipartFile file) throws IOException {
        Product product = productMapper.toEntity(productDTO);
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File uploadPath = new File(uploadDir);
            File targetFile = new File(uploadPath, fileName);

            try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                fos.write(file.getBytes());
            }

            String iconPath = "/images/images_product/" + fileName;
            product.setImage(iconPath);
        }

        String username = getUsername();
        product.setCreatedBy(username);
        product.setUpdatedBy(username);
        product.setCreatedDate(LocalDateTime.now());
        product.setUpdatedDate(LocalDateTime.now());
        productRepository.save(product);
    }

    //xóa product
    @Transactional
    public ProductDTO deleteProducById(int productId) {
        // Kiểm tra xem khách hàng có tồn tại không
        ProductDTO productDTO = getProductById(productId);
        productRepository.deleteById(productId);
        return productDTO;
    }

}
