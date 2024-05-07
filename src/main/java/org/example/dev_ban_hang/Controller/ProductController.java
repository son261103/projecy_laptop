package org.example.dev_ban_hang.Controller;

import org.example.dev_ban_hang.DTO.CategoryDTO;
import org.example.dev_ban_hang.DTO.ProductDTO;
import org.example.dev_ban_hang.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    //hiển thị product
    @GetMapping("/show_product")
    public String showProduct(Model model) {
        List<ProductDTO> productDTOS = productService.ShowAllProducts();
        model.addAttribute("products", productDTOS);
        return "Layout/Product/Product";
    }

    //thêm product
    @GetMapping("/add_product")
    public String showAddProduct(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        List<CategoryDTO> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        return "Layout/Product/add_product";
    }

    @PostMapping("/add_product_s")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO
            , @RequestParam("file") MultipartFile file
            , RedirectAttributes redirectAttributes) {
        String username = productService.getUsername();
        productDTO.setCreatedBy(username);
        productDTO.setUpdatedBy(username);
        productDTO.setCreatedDate(LocalDateTime.now());
        productDTO.setUpdatedDate(LocalDateTime.now());
        productService.addProduct(productDTO, file);
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully");
        return "redirect:/show_product";
    }

    //edit Product
    @GetMapping("/product/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        ProductDTO productDTO = productService.getProductById(id);
        model.addAttribute("productDTO", productDTO);
        List<CategoryDTO> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        return "Layout/Product/edit_product";
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute("productDTO") ProductDTO productDTO, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        productDTO.setId(id);
        try {
            String username = productService.getUsername();
            productDTO.setUpdatedBy(username);
            productDTO.setUpdatedDate(LocalDateTime.now());
            productService.updateProductId(productDTO, file);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/show_product";
    }

    // Phương thức xóa product
    @DeleteMapping("/delete_product/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable int productId) {
        try {
            productService.deleteProducById(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product");
        }
    }
}
