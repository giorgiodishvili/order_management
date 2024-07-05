package com.gv.order.management.dto.response;

import java.math.BigDecimal;

public record OrderResponseDTO(
        Long id, Long userId, String product, Integer quantity, BigDecimal price, String status) {}
