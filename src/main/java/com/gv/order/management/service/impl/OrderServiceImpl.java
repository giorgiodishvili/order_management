package com.gv.order.management.service.impl;

import com.gv.order.management.dto.request.OrderRequestDTO;
import com.gv.order.management.dto.response.OrderResponseDTO;
import com.gv.order.management.exception.OrderNotFoundException;
import com.gv.order.management.mapper.OrderMapper;
import com.gv.order.management.messaging.event.UserUpdatedEvent;
import com.gv.order.management.model.Order;
import com.gv.order.management.repository.OrderRepository;
import com.gv.order.management.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        log.info("Fetching all orders");
        final List<OrderResponseDTO> orders = orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
        log.debug("Fetched orders: {}", orders);
        return orders;
    }

    @Override
    public OrderResponseDTO getOrderById(final Long id) {
        log.info("Fetching order with ID: {}", id);
        return orderRepository.findById(id).map(orderMapper::toOrderResponseDTO).orElseThrow(() -> {
            log.error("Order not found with ID: {}", id);
            return new OrderNotFoundException(id);
        });
    }

    @Override
    public List<OrderResponseDTO> getOrdersForUser(final Long userId) {
        log.info("Fetching orders for user ID: {}", userId);
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO createOrder(final OrderRequestDTO orderRequestDTO) {
        log.info("Creating new order with details: {}", orderRequestDTO);
        final Order order = orderMapper.toOrder(orderRequestDTO);
        final OrderResponseDTO response = orderMapper.toOrderResponseDTO(orderRepository.save(order));
        log.debug("Created order: {}", response);
        return response;
    }

    @Override
    public OrderResponseDTO updateOrder(final Long id, final OrderRequestDTO orderRequestDTO) {
        final Order existingOrder =
                orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        existingOrder.setUserId(orderRequestDTO.userId());
        existingOrder.setProduct(orderRequestDTO.product());
        existingOrder.setQuantity(orderRequestDTO.quantity());
        existingOrder.setPrice(orderRequestDTO.price());
        existingOrder.setStatus(orderRequestDTO.status());
        final Order updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toOrderResponseDTO(updatedOrder);
    }

    @Override
    public void deleteOrder(final Long id) {
        log.info("Deleting order with ID: {}", id);
        if (!orderRepository.existsById(id)) {
            log.error("Order not found with ID: {}", id);
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
        log.debug("Deleted order with ID: {}", id);
    }

    @CacheEvict(value = "orders", allEntries = true)
    @Override
    public void handleUserUpdatedEvent(final UserUpdatedEvent event) {
        if (Objects.requireNonNull(event.getEventType()) == UserUpdatedEvent.EventType.DELETE) {
            orderRepository.deleteOrderByUserId(event.getId());
        }
    }
}
