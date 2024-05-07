package org.example.dev_ban_hang.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.CategoryDTO;
import org.example.dev_ban_hang.DTO.CustomerDTO;
import org.example.dev_ban_hang.Entity.Category;
import org.example.dev_ban_hang.Mapper.CategoryMapper;
import org.example.dev_ban_hang.Repository.CategoryRepository;
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
public class CategoryService {

    public final CategoryRepository categoryRepository;
    public final CategoryMapper categoryMapper;

    //hien thi category
    public List<CategoryDTO> ShowAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryMapper.toDto(categoryList);
    }

    //thêm category
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

    @Value("${image.upload.dir}")
    private String uploadDir;
    @Transactional
    public CategoryDTO addCategory(CategoryDTO categoryDTO, MultipartFile file) {
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
                String iconPath = "/images/images_category/" + fileName; // Đường dẫn cơ bản tới thư mục lưu trữ ảnh
                categoryDTO.setIcon(iconPath);
            }
            // Chuyển đổi CategoryDTO thành Category entity
            Category category = categoryMapper.toEntity(categoryDTO);
            String username = getUsername();
            categoryDTO.setCreatedBy(username);
            categoryDTO.setUpdatedBy(username);
            categoryDTO.setCreatedDate(LocalDateTime.now());
            categoryDTO.setUpdatedDate(LocalDateTime.now());
            // Lưu trữ category vào cơ sở dữ liệu
            category = categoryRepository.save(category);
            // Chuyển đổi Category entity thành CategoryDTO và trả về
            return categoryMapper.toDto(category);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add category", e);
        }
    }

    //edit category
    @Transactional
    public CategoryDTO getCategoryById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục với ID: " + id));

        // Chuyển đổi Category thành CategoryDTO
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        return categoryDTO;
    }


    @Transactional
    public void updateCategory(CategoryDTO categoryDTO, MultipartFile file) throws IOException {
        Category category = categoryMapper.toEntity(categoryDTO);
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File uploadPath = new File(uploadDir);
            File targetFile = new File(uploadPath, fileName);

            try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                fos.write(file.getBytes());
            }

            String iconPath = "/images/images_category/" + fileName;
            category.setIcon(iconPath);
        }

        String username = getUsername();
        category.setCreatedBy(username);
        category.setUpdatedBy(username);
        category.setCreatedDate(LocalDateTime.now());
        category.setUpdatedDate(LocalDateTime.now());

        categoryRepository.save(category);
    }


    //xóa category
    @Transactional
    public CategoryDTO deleteCategoryById(int categoryId) {
        // Kiểm tra xem khách hàng có tồn tại không
        CategoryDTO categoryDTO = getCategoryById(categoryId);
        categoryRepository.deleteById(categoryId);
        return categoryDTO;
    }

}
