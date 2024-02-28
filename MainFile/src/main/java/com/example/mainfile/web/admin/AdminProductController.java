package com.example.mainfile.web.admin;

import com.example.mainfile.dto.StoreDto;
import com.example.mainfile.dto.ProductDto;
import com.example.mainfile.service.StoreService;
import com.example.mainfile.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/stores/products")
public class AdminProductController {

    private final StoreService storeService;
    private final ProductService service;

    @GetMapping("/{id}")
    public String showProductsPage(@PathVariable Integer id, Model model) {
        StoreDto store = storeService.getStoreById(id);
        ProductDto product = ProductDto.builder().store(store).build();
        model.addAttribute("product", product);
        List<ProductDto> products = service.getProductsByStoreId(id);
        model.addAttribute("storeProducts", products);
        model.addAttribute("storeId", store.getStoreId());
        return "adminPageProducts";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductDto product) {
        service.addProduct(product);
        return "redirect:/admin/stores/products/" + product.getStore().getStoreId();
    }

    @PostMapping("/add/{id}")
    public String addProduct(@PathVariable Integer id, @ModelAttribute ProductDto product) {
        StoreDto store = storeService.getStoreById(id);
        product.setStore(store);
        service.addProduct(product);
        return "redirect:/admin/stores/products/" + id;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/getProductById/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return service.getProductById(id);
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ProductDto product = service.getProductById(id);
        Integer storeId = product.getStore().getStoreId();
        service.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "Продукт успешно удален!");
        return "redirect:/admin/stores/products/" + storeId;
    }
}
