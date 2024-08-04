package com.minyan.currencycapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类 统一日志实现，优化日志级别，增加异常处理，设置Value序列化器，提高代码的可维护性和清晰度
 *
 * @author minyan.he
 * @date 2024/8/4 11:52
 */
@Configuration
@ComponentScan(basePackages = "com.minyan")
@Slf4j // 统一日志实现，使用Lombok提供的log
public class RedisConfiguration {

  /**
   * 初始化RedisTemplate 设置Key和Value序列化器，并设置异常处理
   *
   * @param redisConnectionFactory Redis连接工厂
   * @return 配置好的RedisTemplate
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    log.info("[RedisConfiguration][redisTemplate]开始创建redis模板对象..."); // 优化日志级别为debug
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    try {
      // 设置redis的连接工厂对象
      redisTemplate.setConnectionFactory(redisConnectionFactory);
      // 设置redis key的序列化器
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      // 设置redis value的序列化器
      redisTemplate.setValueSerializer(new StringRedisSerializer());
      redisTemplate.afterPropertiesSet();
    } catch (Exception e) {
      log.error("[RedisConfiguration][redisTemplate]RedisTemplate配置失败", e); // 添加异常处理
      throw e; // 重新抛出异常，确保调用者可以处理
    }
    return redisTemplate;
  }
}
