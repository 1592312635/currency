package com.minyan.currencycrond.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.minyan.Enum.CodeEnum;
import com.minyan.Enum.OrderStatusEnum;
import com.minyan.currencycrond.handler.expire.CurrencyExpireAbstractHandler;
import com.minyan.currencycrond.service.CurrencyExpireTaskService;
import com.minyan.dao.CurrencyOrderMapper;
import com.minyan.exception.CustomException;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.vo.context.expire.ExpireContext;
import java.math.BigDecimal;
import java.util.List;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @decription 代币过期处理service
 * @author minyan.he
 * @date 2024/8/1 13:59
 */
@Service
public class CurrencyExpireTaskServiceImpl implements CurrencyExpireTaskService {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyExpireTaskServiceImpl.class);
  @Autowired private CurrencyOrderMapper currencyOrderMapper;
  @Autowired private List<CurrencyExpireAbstractHandler> currencyExpireHandlers;

  @SneakyThrows
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void expireCurrencyOrder(CurrencyOrderPO currencyOrderPO) {
    // 先计算需要过期金额
    BigDecimal expireAmount =
        currencyOrderPO
            .getAmount()
            .subtract(currencyOrderPO.getFailAmount())
            .subtract(currencyOrderPO.getExpireAmount());
    // 如果没有需要过期的金额，直接变更订单状态即可
    if (expireAmount.compareTo(BigDecimal.ZERO) <= 0) {
      CurrencyOrderPO updateCurrencyOrderPO = new CurrencyOrderPO();
      updateCurrencyOrderPO.setId(currencyOrderPO.getId());
      updateCurrencyOrderPO.setStatus(OrderStatusEnum.EXPIRE.getValue());
      currencyOrderMapper.updateStatusAndAmountById(currencyOrderPO);
      logger.info(
          "[CurrencyExpireTaskServiceImpl][expireCurrencyOrder]当前订单无待过期金额，直接置为过期，订单号：{}",
          currencyOrderPO.getOrderNo());
      return;
    }
    // 需要过期订单做后续操作
    ExpireContext expireContext = new ExpireContext();
    expireContext.setExpireOrderPO(currencyOrderPO);
    List<CurrencyExpireAbstractHandler> fallBackHandlers = Lists.newArrayList();
    for (CurrencyExpireAbstractHandler currencyExpireHandler : currencyExpireHandlers) {
      fallBackHandlers.add(currencyExpireHandler);
      if (!currencyExpireHandler.handle(expireContext)) {
        for (CurrencyExpireAbstractHandler fallBackHandler : fallBackHandlers) {
          try {
            fallBackHandler.fallBack(expireContext);
          } catch (Exception e) {
            logger.info(
                "[CurrencyExpireTaskServiceImpl][expireCurrencyOrder]代币过期失败，回退时发生异常，订单信息：{}",
                JSONObject.toJSONString(currencyOrderPO),
                e);
            throw new CustomException(CodeEnum.EXPIRE_EXCEPTION);
          }
        }
        return;
      }
    }
    logger.info(
        "[CurrencyExpireTaskServiceImpl][expireCurrencyOrder]订单过期处理完成，订单号：{}",
        currencyOrderPO.getOrderNo());
  }
}
