package com.gv.order.management.mapper;

import com.gv.order.management.config.MapStructConfig;
import com.gv.order.management.dto.request.OrderRequestDTO;
import com.gv.order.management.dto.response.OrderResponseDTO;
import com.gv.order.management.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface OrderMapper {

    OrderResponseDTO toOrderResponseDTO(Order order);

    @Mapping(target = "id", ignore = true)
    Order toOrder(OrderRequestDTO orderRequestDTO);
}
