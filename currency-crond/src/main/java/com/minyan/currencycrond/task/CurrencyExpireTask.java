package com.minyan.currencycrond.task;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.CycleEnum;
import com.minyan.Enum.HandleTypeEnum;
import com.minyan.Enum.OrderStatusEnum;
import com.minyan.Enum.RedisKeyEnum;
import com.minyan.currencycrond.service.CurrencyExpireTaskService;
import com.minyan.dao.CurrencyOrderMapper;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.service.RedisService;
import com.minyan.utils.TimeUtil;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @decription 代币过期任务
 * @author minyan.he
 * @date 2024/7/4 14:52
 */
@RestController
public class CurrencyExpireTask {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyExpireTask.class);
  @Autowired private CurrencyExpireTaskService currencyExpireTaskService;
  @Autowired private CurrencyOrderMapper currencyOrderMapper;
  @Autowired @Lazy private RedisService redisService;

  @Scheduled(cron = "0/10 * * * * ?")
  public void expireCurrencyCrond() {
    List<CurrencyOrderPO> expireOrders =
        currencyOrderMapper.queryExpireOrders(
            HandleTypeEnum.ADD.getValue(),
            Arrays.asList(OrderStatusEnum.DEFAULT.getValue(), OrderStatusEnum.SUCCESS.getValue()),
            1000);
    logger.info(
        "[CurrencyExpireTask][expireCurrencyCrond]查询代币过期订单条数：{}，开始处理代币过期~",
        CollectionUtils.isEmpty(expireOrders) ? 0 : expireOrders.size());
    for (CurrencyOrderPO expireOrder : expireOrders) {
      if (redisService.lock(
          String.format(
              "%s:%s",
              RedisKeyEnum.CURRENCY_ORDER_EXPIRE_REDIS_KEY.getKey(), expireOrder.getOrderNo()))) {
        try {
          logger.info(
              "[CurrencyExpireTask][expireCurrencyCrond]当前处理代币过期订单：{}",
              JSONObject.toJSONString(expireOrder));
          currencyExpireTaskService.expireCurrencyOrder(expireOrder);
        } catch (Exception e) {
          // 异常情况调度时间后移30分钟
          logger.error(
              "[CurrencyExpireTask][expireCurrencyCrond]查询代币过期结果：{}，处理代币过期异常~",
              JSONObject.toJSONString(expireOrder),
              e);
          CurrencyOrderPO updateCurrencyOrderPO = new CurrencyOrderPO();
          updateCurrencyOrderPO.setId(expireOrder.getId());
          currencyOrderMapper.updateScheduleTimeById(
              expireOrder.getId(),
              TimeUtil.adjustDate(expireOrder.getExpireTime(), CycleEnum.MINUTES, 30L));
        } finally {
          redisService.releaseLock(
              String.format(
                  "%s:%s",
                  RedisKeyEnum.CURRENCY_ORDER_EXPIRE_REDIS_KEY.getKey(), expireOrder.getOrderNo()));
        }
      }
    }
  }
}
