package com.gv.order.management;

import com.gv.order.management.config.ContainersConfig;
import org.springframework.boot.SpringApplication;

public class LocalDevApplication {
    public static void main(String[] args) {
        SpringApplication.from(OrderManagementApplication::main)
                .with(ContainersConfig.class)
                .run(args);
    }
}
