package com.minyan.currencycapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.minyan.dao")
public class CurrencyCapiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CurrencyCapiApplication.class, args);
  }
}
