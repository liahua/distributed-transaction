package com.chenahua.distributedtransaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = "com.chenahua.distributedtransaction.mapper")
@SpringBootApplication()
public class DistributedTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedTransactionApplication.class, args);
    }

}
