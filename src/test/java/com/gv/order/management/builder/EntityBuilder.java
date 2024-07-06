package com.gv.order.management.builder;

import com.gv.order.management.model.Order;

import java.math.BigDecimal;

public class EntityBuilder {
    public static Order buildOrder() {
        final Order order = new Order();
        order.setUserId(1L);
        order.setProduct("Test Product");
        order.setQuantity(5);
        order.setPrice(BigDecimal.valueOf(100.0));
        order.setStatus("NEW");
        return order;
    }
}
