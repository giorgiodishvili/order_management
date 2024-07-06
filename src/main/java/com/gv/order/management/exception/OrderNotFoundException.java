package com.gv.order.management.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.Serial;

public class OrderNotFoundException extends HttpStatusCodeException {

    @Serial
    private static final long serialVersionUID = 7915857397335290657L;

    public OrderNotFoundException(final Long orderId) {
        super(HttpStatusCode.valueOf(404), "Order not found with ID: " + orderId);
    }
}
