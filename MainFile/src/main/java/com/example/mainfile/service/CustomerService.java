package com.example.mainfile.service;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.CustomerDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.CustomerEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.mapper.CustomerMapper;
import com.example.mainfile.repository.BookingRepository;
import com.example.mainfile.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final CustomerMapper customerMapper;
    private final BookingMapper bookingMapper;

    public void saveCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
        customerRepository.save(customerEntity);
    }

    public List<BookingDto> getBookings() {
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        return bookingEntities.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomer(Integer customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return customerMapper.toDto(customerEntity);
    }

    public CustomerDto getCurrentCustomer() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();


        CustomerEntity customerEntity = customerRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));


        return customerMapper.toDto(customerEntity);
    }
}
