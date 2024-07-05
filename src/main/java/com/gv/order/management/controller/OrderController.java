package com.gv.order.management.controller;

import com.gv.order.management.dto.request.OrderRequestDTO;
import com.gv.order.management.dto.response.OrderResponseDTO;
import com.gv.order.management.filter.LoggedInUser;
import com.gv.order.management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Cacheable(value = "orders")
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/me")
    public List<OrderResponseDTO> getOrdersForUser(@AuthenticationPrincipal final LoggedInUser loggedInUser) {
        final Long id = loggedInUser.getId();
        return orderService.getOrdersForUser(id);
    }

    @GetMapping("/user/{userId}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public List<OrderResponseDTO> getOrdersForUserById(@PathVariable final Long userId) {
        return orderService.getOrdersForUser(userId);
    }

    @Cacheable(value = "orders", key = "#id")
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public OrderResponseDTO getOrderById(@PathVariable final Long id) {
        return orderService.getOrderById(id);
    }

    @CacheEvict(value = "orders", allEntries = true)
    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody final OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @CacheEvict(value = "orders", key = "#id")
    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public OrderResponseDTO updateOrder(
            @PathVariable final Long id, @RequestBody final OrderRequestDTO orderRequestDTO) {
        return orderService.updateOrder(id, orderRequestDTO);
    }

    @CacheEvict(value = "orders", key = "#id")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteOrder(@PathVariable final Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
