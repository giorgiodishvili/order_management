package com.gv.order.management.service;

import com.gv.order.management.dto.request.OrderRequestDTO;
import com.gv.order.management.dto.response.OrderResponseDTO;
import com.gv.order.management.messaging.event.UserUpdatedEvent;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO getOrderById(Long id);

    List<OrderResponseDTO> getOrdersForUser(Long userId);

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO);

    void deleteOrder(Long id);

    void handleUserUpdatedEvent(UserUpdatedEvent event);
}
