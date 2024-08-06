package com.minyan.currencycrond.config;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @decription
 * @author minyan.he
 * @date 2024/8/5 12:35
 */
@Getter
@Configuration
public class ThreadPoolConfig {

  /** 核心线程数 */
  @Value("${thread.pool.executor.config.corePoolSize}")
  private Integer corePoolSize;

  /** 最大线程数 */
  @Value("${thread.pool.executor.config.maxPoolSize}")
  private Integer maxPoolSize;

  /** 存活时间 */
  @Value("${thread.pool.executor.config.keepAliveTime}")
  private Integer keepAliveTime;

  /** 最大队列数 */
  @Value("${thread.pool.executor.config.blockQueueSize}")
  private Integer blockQueueSize;

  /*
   * AbortPolicy：丢弃任务并抛出RejectedExecutionException异常。
   * DiscardPolicy：直接丢弃任务，但是不会抛出异常
   * DiscardOldestPolicy：将最早进入队列的任务删除，之后再尝试加入队列的任务被拒绝
   * CallerRunsPolicy：如果任务添加线程池失败，那么主线程自己执行该任务
   * */
  @Value("${thread.pool.executor.config.policy}")
  private String policy;

  @Bean("commonThreadPoolExecutor")
  public Executor commonThreadPoolExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    // 设置线程池参数信息
    taskExecutor.setCorePoolSize(corePoolSize);
    taskExecutor.setMaxPoolSize(maxPoolSize);
    taskExecutor.setQueueCapacity(blockQueueSize);
    taskExecutor.setKeepAliveSeconds(keepAliveTime);
    taskExecutor.setThreadNamePrefix("[ThreadPoolConfig][commonThreadPoolExecutor]");
    taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    taskExecutor.setAwaitTerminationSeconds(keepAliveTime);
    RejectedExecutionHandler handler;
    switch (policy) {
      case "AbortPolicy":
        handler = new ThreadPoolExecutor.AbortPolicy();
        break;
      case "DiscardPolicy":
        handler = new ThreadPoolExecutor.DiscardPolicy();
        break;
      case "DiscardOldestPolicy":
        handler = new ThreadPoolExecutor.DiscardOldestPolicy();
        break;
      case "CallerRunsPolicy":
        handler = new ThreadPoolExecutor.CallerRunsPolicy();
        break;
      default:
        handler = new ThreadPoolExecutor.AbortPolicy();
        break;
    }
    // 修改拒绝策略为使用当前线程执行
    taskExecutor.setRejectedExecutionHandler(handler);
    // 初始化线程池
    taskExecutor.initialize();
    return taskExecutor;
  }
}
