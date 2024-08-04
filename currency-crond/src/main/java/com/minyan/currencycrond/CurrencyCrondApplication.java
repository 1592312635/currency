package com.minyan.currencycrond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.minyan.currencycrond")
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableAutoConfiguration
@MapperScan(basePackages = "com.minyan.dao")
public class CurrencyCrondApplication {

  public static void main(String[] args) {
    SpringApplication.run(CurrencyCrondApplication.class, args);
  }
}
