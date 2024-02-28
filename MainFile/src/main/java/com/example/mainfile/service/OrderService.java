package com.example.mainfile.service;

import com.example.mainfile.dto.OrderDto;
import com.example.mainfile.dto.ProductDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.OrderEntity;
import com.example.mainfile.mapper.OrderMapper;
import com.example.mainfile.repository.OrderRepository;
import com.example.mainfile.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final UserService userService;
    private final UserRepository userRepository;


    public void save(OrderDto order) {
        if (order.getProduct() == null) {
            throw new IllegalArgumentException("ProductId cannot be null for an order");
        }
        OrderEntity save = orderRepository.saveAndFlush(orderMapper.toEntity(order));
        orderMapper.toDto(save);
    }

    public void saveOrder(Integer productId, OrderDto orderDto, UUID userId, int quantity) {
        Optional<UserDto> optionalUser = userService.getById(userId);
        ProductDto product = productService.getProductById(productId);

        if (optionalUser.isPresent()) {
            UserDto user = optionalUser.get();

            OrderDto order = new OrderDto();
            order.setProduct(product);
            order.setUser(user);
            order.setQuantity(quantity);

            orderRepository.save(orderMapper.toEntity(order));
        }
    }

    public void updateOrder(OrderDto existingOrder, int additionalQuantity) {

        existingOrder.setQuantity(existingOrder.getQuantity() + additionalQuantity);
        orderRepository.save(orderMapper.toEntity(existingOrder));
    }


    public List<OrderDto> getOrdersForUser(UUID userId) {
        List<OrderEntity> orders = orderRepository.findAllByUserId(userId);
        return orderMapper.toListDto(orders);
    }

    public Optional<OrderDto> findOrderForProduct(UUID userId, Integer productId) {

        List<OrderDto> userOrders = orderMapper.toListDto(orderRepository.findAllByUserId(userId));

        for (OrderDto order : userOrders) {
            if (order.getProduct().getProductId().equals(productId)) {
                return Optional.of(order);
            }
        }

        return Optional.empty();
    }

}
