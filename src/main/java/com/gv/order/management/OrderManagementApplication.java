package com.gv.order.management;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@RequiredArgsConstructor
@SpringBootApplication
@EnableFeignClients(basePackages = "com.gv.order.management.client")
@EnableCaching
@EnableKafka
public class OrderManagementApplication {

    public static void main(final String[] args) {
        SpringApplication.run(OrderManagementApplication.class, args);
    }
}
