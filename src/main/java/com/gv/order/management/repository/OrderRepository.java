package com.gv.order.management.repository;

import com.gv.order.management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);

    @Modifying
    void deleteOrderByUserId(Long userId);
}
