package com.gv.order.management.repository;

import com.gv.order.management.builder.EntityBuilder;
import com.gv.order.management.config.ContainersConfig;
import com.gv.order.management.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(ContainersConfig.class)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveAndFindById() {
        Order order = EntityBuilder.buildOrder();
        orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertTrue(foundOrder.isPresent());
        assertEquals(order.getProduct(), foundOrder.get().getProduct());
    }

    @Test
    public void testDeleteById() {
        Order order = EntityBuilder.buildOrder();
        orderRepository.save(order);

        orderRepository.deleteById(order.getId());
        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertFalse(foundOrder.isPresent());
    }
}
