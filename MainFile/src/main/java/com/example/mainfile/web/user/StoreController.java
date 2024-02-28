package com.example.mainfile.web.user;

import com.example.mainfile.dto.*;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService service;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String showStoreForm(Model model, @AuthenticationPrincipal UserEntity userEntity) {
        model.addAttribute("store", new StoreDto());
        model.addAttribute("stores", service.getAllStores());

        if (userEntity != null) {
            Optional<UserDto> optionalUser = userService.getById(userEntity.getId());
            if (optionalUser.isPresent()) {
                UserDto user = optionalUser.get();
                model.addAttribute("user", user);
            }
        }

        return "storesPage";
    }

    @GetMapping("/{id}")
    public String showStoreDetails(@PathVariable Integer id, Model model) {
        StoreDto store = service.getStoreById(id);
        List<ProductDto> products = productService.getProductsByStoreId(id);
        model.addAttribute("store", store);
        model.addAttribute("products", products);
        return "storePage";
    }

    @PostMapping("/{id}")
    public String updateStoreDetails(@PathVariable Integer id, @ModelAttribute StoreDto updatedStore, Model model) {
        service.updateStore(id, updatedStore);
        StoreDto store = service.getStoreById(id);
        List<ProductDto> products = productService.getProductsByStoreId(id);
        model.addAttribute("store", store);
        model.addAttribute("products", products);
        return "redirect:/stores/" + id;
    }

    @PostMapping("/orderProduct")
    public String orderProduct(@RequestParam Integer productId, @RequestParam int quantity,
                               @ModelAttribute OrderDto orderDto,
                               @AuthenticationPrincipal UserEntity userEntity,
                               Model model) {

        ProductDto product = productService.getProductById(productId);
        if (product.getQuantity() < quantity) {
            model.addAttribute("quantityError", "Вы заказываете товара больше, чем есть на складе");
            return "redirect:/stores";
        }
        if (userEntity != null) {
            Optional<UserDto> optionalUser = userService.getById(userEntity.getId());

            if (optionalUser.isPresent()) {
                UserDto user = optionalUser.get();
                orderDto.setUser(user);

                Optional<OrderDto> existingOrder = orderService.findOrderForProduct(user.getId(), productId);
                if (existingOrder.isPresent()) {
                    orderService.updateOrder(existingOrder.get(), quantity);
                } else {
                    orderService.saveOrder(productId, orderDto, user.getId(), quantity);
                }

                product.setQuantity(product.getQuantity() - quantity);
                productService.updateProduct(product.getProductId(), product);
            }
        }

        return "redirect:/stores";
    }



    @GetMapping("/search")
    public String searchStores(@RequestParam String searchName, Model model, @AuthenticationPrincipal UserEntity userEntity) {
        List<StoreDto> stores = service.searchStores(searchName);
        model.addAttribute("stores", stores);
        model.addAttribute("store", new StoreDto());

        if (userEntity != null) {
            Optional<UserDto> optionalUser = userService.getById(userEntity.getId());
            if (optionalUser.isPresent()) {
                UserDto user = optionalUser.get();
                model.addAttribute("user", user);
            }
        }

        return "storesPage";
    }


}
