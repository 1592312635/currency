package com.minyan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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
    log.info("[RedisConfiguration][redisTemplate]开始创建redis模板对象...");
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    try {
      // 设置redis的连接工厂对象
      redisTemplate.setConnectionFactory(redisConnectionFactory);

      // 使用 Jackson 2 JSON 序列化器
      Jackson2JsonRedisSerializer<Object> serializer =
          new Jackson2JsonRedisSerializer<>(Object.class);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.findAndRegisterModules(); // 注册 Jackson 模块
      serializer.setObjectMapper(objectMapper);

      // 设置redis key的序列化器
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      // 设置redis value的序列化器
      redisTemplate.setValueSerializer(serializer); // 修改这里
      redisTemplate.afterPropertiesSet();
    } catch (Exception e) {
      log.error("[RedisConfiguration][redisTemplate]RedisTemplate配置失败", e);
      throw e;
    }
    return redisTemplate;
  }
}
