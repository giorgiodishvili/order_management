package com.gv.order.management.builder;

import com.gv.order.management.dto.request.OrderRequestDTO;
import com.gv.order.management.dto.response.OrderResponseDTO;

import java.math.BigDecimal;
import java.util.Random;

public class DtoBuilder {

    private static final Random RANDOM = new Random();

    public static OrderResponseDTO generateOrderResponseDTO() {
        return new OrderResponseDTO(
                RANDOM.nextLong(),
                RANDOM.nextLong(),
                "Product " + RANDOM.nextInt(100),
                RANDOM.nextInt(10) + 1,
                BigDecimal.valueOf(RANDOM.nextDouble() * 100),
                "NEW");
    }

    public static OrderRequestDTO generateOrderRequestDTO() {
        return new OrderRequestDTO(
                RANDOM.nextLong(),
                "Product " + RANDOM.nextInt(100),
                RANDOM.nextInt(10) + 1,
                BigDecimal.valueOf(RANDOM.nextDouble() * 100),
                "NEW");
    }
}
