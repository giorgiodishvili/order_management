package com.gv.order.management.service.impl;

import com.gv.order.management.dto.request.OrderRequestDTO;
import com.gv.order.management.dto.response.OrderResponseDTO;
import com.gv.order.management.mapper.OrderMapper;
import com.gv.order.management.messaging.event.UserUpdatedEvent;
import com.gv.order.management.model.Order;
import com.gv.order.management.repository.OrderRepository;
import com.gv.order.management.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrderById(final Long id) {
        final Order order =
                orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toOrderResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersForUser(final Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO createOrder(final OrderRequestDTO orderRequestDTO) {
        final Order order = orderMapper.toOrder(orderRequestDTO);
        final Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponseDTO(savedOrder);
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
        orderRepository.deleteById(id);
    }

    @CacheEvict(value = "orders", allEntries = true)
    @Override
    public void handleUserUpdatedEvent(final UserUpdatedEvent event) {
        if (Objects.requireNonNull(event.getEventType()) == UserUpdatedEvent.EventType.DELETE) {
            orderRepository.deleteOrderByUserId(event.getId());
        }
    }
}
