//package com.minyan.currencycapi.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * @decription redis序列化配置
// * @author minyan.he
// * @date 2024/7/9 14:19
// */
//@Configuration
//public class RedisConfig {
//
//  @Bean
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//    RedisTemplate<String, Object> template = new RedisTemplate<>();
//    template.setConnectionFactory(factory);
//
//    Jackson2JsonRedisSerializer<Object> serializer =
//        new Jackson2JsonRedisSerializer<>(Object.class);
//    ObjectMapper mapper = new ObjectMapper();
//    mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//    mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//    serializer.setObjectMapper(mapper);
//
//    template.setValueSerializer(serializer);
//    template.setKeySerializer(new StringRedisSerializer());
//    template.afterPropertiesSet();
//    return template;
//  }
//}
