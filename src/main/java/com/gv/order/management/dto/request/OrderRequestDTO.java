package com.gv.order.management.dto.request;

import java.math.BigDecimal;

public record OrderRequestDTO(Long userId, String product, Integer quantity, BigDecimal price, String status) {}
