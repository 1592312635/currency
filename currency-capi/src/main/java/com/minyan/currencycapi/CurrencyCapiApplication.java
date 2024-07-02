package com.minyan.currencycapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableAsync
@EnableCaching
@MapperScan(basePackages = "com.minyan.dao")
public class CurrencyCapiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CurrencyCapiApplication.class, args);
  }
}
