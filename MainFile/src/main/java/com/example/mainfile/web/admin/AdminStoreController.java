package com.example.mainfile.web.admin;

import com.example.mainfile.dto.StoreDto;
import com.example.mainfile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/stores")
public class AdminStoreController {

    private final StoreService service;

    @GetMapping("/addStore")
    public String showAddStoreForm(Model model) {
        model.addAttribute("store", new StoreDto());
        model.addAttribute("stores", service.getAllStores());
        return "adminPageStores";
    }

    @GetMapping
    public List<StoreDto> getAllStores() {
        return service.getAllStores();
    }

    @GetMapping("/{id}")
    public StoreDto getStoreById(@PathVariable Integer id) {
        return service.getStoreById(id);
    }

    @PostMapping("/addStore")
    public String addStore(@ModelAttribute("store") StoreDto storeDto, RedirectAttributes redirectAttributes,
                           @RequestParam(value = "file") MultipartFile file) throws IOException {
        storeDto.setLogo(file.getBytes());
        service.createStore(storeDto);
        redirectAttributes.addFlashAttribute("message", "Магазин успешно добавлен!");
        return "redirect:/admin/stores/addStore";
    }

    @PostMapping("/delete/{id}")
    public String deleteStore(@PathVariable Integer id) {
        service.deleteStore(id);
        return "redirect:/admin/stores/addStore";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        StoreDto storeDto = service.getStoreById(id);
        model.addAttribute("store", storeDto);
        return "adminStoreUpdate";
    }

    @PostMapping("/update/{id}")
    public String updateStore(@PathVariable("id") Integer id, @ModelAttribute("store") StoreDto storeDto,
                              @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] imageBytes = file.getBytes();
            storeDto.setLogo(imageBytes);
        }
        storeDto.setName(storeDto.getName());
        storeDto.setAddress(storeDto.getAddress());
        storeDto.setRating(storeDto.getRating());
        service.updateStore(id, storeDto);
        return "redirect:/admin/stores/addStore";
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchName") String searchName, Model model) {
        List<StoreDto> stores = service.searchStores(searchName);
        model.addAttribute("stores", stores);
        model.addAttribute("store", new StoreDto());
        return "adminPageStores";
    }

    @GetMapping("/sort")
    public String sort(@RequestParam("sortRating") String sortRating, Model model) {
        List<StoreDto> stores;
        if ("asc".equals(sortRating)) {
            stores = service.sortStoresAscending();
        } else {
            stores = service.sortStoresDescending();
        }
        model.addAttribute("stores", stores);
        model.addAttribute("store", new StoreDto());
        return "adminPageStores";
    }
}
