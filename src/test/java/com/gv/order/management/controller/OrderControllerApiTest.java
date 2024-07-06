package com.gv.order.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gv.order.management.builder.DtoBuilder;
import com.gv.order.management.config.ContainersConfig;
import com.gv.order.management.dto.request.OrderRequestDTO;
import com.gv.order.management.dto.response.OrderResponseDTO;
import com.gv.order.management.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ContainersConfig.class)
@AutoConfigureMockMvc
public class OrderControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Mockito.reset(orderService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllOrdersForUser() throws Exception {

        mockMvc.perform(get("/api/orders")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetOrderById() throws Exception {
        OrderResponseDTO orderResponseDTO = DtoBuilder.generateOrderResponseDTO();
        when(orderService.getOrderById(anyLong())).thenReturn(orderResponseDTO);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderResponseDTO.id()));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetOrderByIdForUser() throws Exception {
        mockMvc.perform(get("/api/orders/1")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateOrder() throws Exception {
        OrderRequestDTO orderRequestDTO = DtoBuilder.generateOrderRequestDTO();
        OrderResponseDTO orderResponseDTO = DtoBuilder.generateOrderResponseDTO();
        when(orderService.createOrder(any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(orderRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderResponseDTO.id()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/1")).andExpect(status().isNoContent());
    }
}
