package org.example.dev_ban_hang.Controller;

import org.example.dev_ban_hang.DTO.CategoryDTO;
import org.example.dev_ban_hang.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // hiển thị category
    @GetMapping("/show_category")
    public String showCustomers(Model model) {
        List<CategoryDTO> categoryDTOS = categoryService.ShowAllCategories();
        model.addAttribute("categoryDTOS", categoryDTOS);
        return "Layout/Category/Category";
    }

    // thêm category
    @GetMapping("/add_category")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("categoryDTO", new CategoryDTO());
        return "Layout/Category/add_category";
    }

    @PostMapping("/add_category_s")
    public String addCategory(@ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
                              @RequestParam("file") MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        String username = categoryService.getUsername();
        categoryDTO.setCreatedBy(username);
        categoryDTO.setUpdatedBy(username);
        categoryDTO.setCreatedDate(LocalDateTime.now());
        categoryDTO.setUpdatedDate(LocalDateTime.now());
        categoryService.addCategory(categoryDTO, file);
        redirectAttributes.addFlashAttribute("successMessage", "Category added successfully");
        return "redirect:/show_category";
    }


    //edit category
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        model.addAttribute("categoryDTO", categoryDTO);
        return "Layout/Category/edit_category";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int id, @ModelAttribute("categoryDTO") CategoryDTO categoryDTO, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        categoryDTO.setId(id);
        try {
            categoryService.updateCategory(categoryDTO, file);
            redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/show_category";
    }

    // Phương thức xóa category

    @DeleteMapping("/delete_category/{categoryId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            // Xử lý trường hợp xóa không thành công
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete category");
        }
    }

}
