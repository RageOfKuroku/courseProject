package com.example.mainfile.web.user;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.CustomerDto;
import com.example.mainfile.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public String showForm(@PathVariable Integer customerId, Model model) {
        CustomerDto customerDto = customerService.getCustomer(customerId);
        model.addAttribute("customer", customerDto);
        return "customerPage";
    }



    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") CustomerDto customerDto) {
        customerService.saveCustomer(customerDto);
        return "redirect:/customer";
    }

    @ModelAttribute("bookings")
    public List<BookingDto> getBookings() {
        return customerService.getBookings();
    }
}


